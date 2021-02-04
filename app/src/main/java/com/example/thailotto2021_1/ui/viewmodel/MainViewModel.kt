package com.example.thailotto2021_1.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.other.Constants.CHECK_SINGLE_MODE
import com.example.thailotto2021_1.other.Event
import com.example.thailotto2021_1.other.Resource
import com.example.thailotto2021_1.repository.LotteryRepository
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.lang.StringBuilder
import java.util.*

class MainViewModel @ViewModelInject constructor(
    val repository : LotteryRepository
) : ViewModel() {

    //private val _allLotteryResult = MutableLiveData<List<LotteryResult>>()
    var allLotteryResult : LiveData<List<LotteryResult>> = repository.getAllLotteryResult()

    val fm = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
            .withChronology(ThaiBuddhistChronology.INSTANCE)
    val listOfSpinner = Transformations.map(allLotteryResult){
        val listR : MutableList<String> = mutableListOf()
        for(item in it){
            listR.add(LocalDate.ofEpochDay(item.date).format(fm))
        }
        return@map listR
    }
    private val _insertStatus = MutableLiveData<Event<Resource<Lottery>>>()
    val insertStatus : LiveData<Event<Resource<Lottery>>> = _insertStatus

    private val _lotteryResultByDrawDate = MutableLiveData<LotteryResult>()
    var lotteryResultByDrawDate : LiveData<LotteryResult> = _lotteryResultByDrawDate

    private val _checkedResult = MutableLiveData<Event<Unit>>()

    private val _positionInSpinner = MutableLiveData<Int>()
    var positionInSpinner : LiveData<Int> = _positionInSpinner

    private val _navigateToCheckingResultFragment = MutableLiveData<Event<Lottery>>()
    val navigateToCheckingResultFragment : LiveData<Event<Lottery>> = _navigateToCheckingResultFragment

    private val _noDrawNumber = MutableLiveData<Event<String>>()
    val noDrawNumber : LiveData<Event<String>> = _noDrawNumber

    private val _emptyLotteryResults = MutableLiveData<Event<String>>()
    val emptyLotteryResults : LiveData<Event<String>> = _emptyLotteryResults


    init {
        getAllLotteryResult()
    }

    val getAllLottery = repository.getAllLottery()

    val isEmptyResult = Transformations.map(allLotteryResult){
        it == emptyList<Lottery>()
    }

//    fun getAllLotteryResult(){
//        repository.getAllLotteryResult().orderBy("date",Query.Direction.DESCENDING)
//            .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
//            if(error!=null){
//                Timber.d(error.message)
//                return@addSnapshotListener
//            }
//
//            val list = value!!.toObjects(LotteryResult::class.java)
//            _allLotteryResult.postValue(list)
//
//        }
//    }
    fun savePositionInSpinner(position : Int){
    _positionInSpinner.postValue(position)
}
    fun getAllLotteryResult(){
        allLotteryResult =repository.getAllLotteryResult()
        Timber.d("start listener")
    }



    fun insertLottery(lottery: Lottery) = viewModelScope.launch {
        repository.insertLottery(lottery)
        _insertStatus.postValue(Event(Resource.success(lottery)))
    }

    fun deleteLottery(lottery: Lottery) = viewModelScope.launch {
        repository.deleteLottery(lottery)
    }

    fun getLotteryResultByDrawDate(thaidate : String){
        val fm2 = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))

        val a = allLotteryResult.value?.filter {
            it.date== LocalDate.parse(thaidate,fm2).toEpochDay() - 198326
        }
//        Timber.d("${a?.get(0)}")
        _lotteryResultByDrawDate.postValue(a?.get(0))

    }

    fun getLotteryResultByDrawNumberAndCheck(drawNumber : String, userLottery : String){

         val a = allLotteryResult.value?.filter {
            it.drawNumber.contains(drawNumber)
        }

        if (a.isNullOrEmpty()){
            Timber.d("งวด $drawNumber ยังไม่ออก")
            _noDrawNumber.postValue(Event("งวดที่คุณเลือกยังไม่ออก"))
            return
        }else{
            _lotteryResultByDrawDate.postValue(a[0])
            checkLottery(userLottery,CHECK_SINGLE_MODE)
        }

        Timber.d("งวดที่มี $drawNumber คือ ${a.toString()}")

    }

    fun checkLottery(input : String,mode : String) : Lottery{
        Timber.d("mode : $mode")
        var lottery : Lottery = Lottery(0,"")
        if(input.length!=6){
            _insertStatus.postValue(Event(Resource.error("กรุณาใส่เลข 6 หลัก",null)))
            return lottery
        }

        val userFirst3digit = input.substring(0,3)
        val userLast3digit = input.substring(3,6)
        val userLast2digit = input.substring(4,6)

        val prize1st = lotteryResultByDrawDate.value?.prize1st
        val nearBy1stPrize = lotteryResultByDrawDate.value?.nearBy1stPrize
        val prizeFirst3Digits = lotteryResultByDrawDate.value?.prizeFirst3Digit
        val prizeLast3Digits = lotteryResultByDrawDate.value?.prizeLast3Digit
        val prize2Digits = lotteryResultByDrawDate.value?.prizeLast2Digit
        val prize2nd = lotteryResultByDrawDate.value?.prize2nd
        val prize3rd = lotteryResultByDrawDate.value?.prize3rd
        val prize4th = lotteryResultByDrawDate.value?.prize4th
        val prize5th = lotteryResultByDrawDate.value?.prize5th

        val isWonPrize1st = (prize1st?.contains(input))
        val isWonNearBy1stPrize = nearBy1stPrize?.contains(input)
        val isWonFirst3digit = prizeFirst3Digits?.contains(userFirst3digit)
        val isWonLast3digit = prizeLast3Digits?.contains(userLast3digit)
        val isWonLast2digitPrize = prize2Digits?.contains(userLast2digit)
        val isWonPrize2nd = prize2nd?.contains(input)
        val isWonPrize3rd = prize3rd?.contains(input)
        val isWonPrize4th = prize4th?.contains(input)
        val isWonPrize5th = prize5th?.contains(input)

        Timber.d("ถูกรางวัลที่ 1? : ${isWonPrize1st.toString()} ,ถูกเลขท้าย? : ${isWonLast2digitPrize.toString()},,รางวัลที่ 2 ?: ${isWonPrize2nd.toString()}")

        if(lotteryResultByDrawDate.value != null) { val userLottery = Lottery(lotteryResultByDrawDate.value?.date!!,input)
            userLottery.apply {
                //thai_date = lotteryResultByDrawDate.value?.thai_date!!
                isCheck=true
                win1stPrizeReward = if (isWonPrize1st==true) 6000000 else 0
                winDigitsNearBy1stPrizeReward = if(isWonNearBy1stPrize == true) 100000 else 0
                win2ndPrizeReward = if(isWonPrize2nd == true) 200000 else 0
                win3rdPrizeReward = if (isWonPrize3rd == true) 80000 else 0
                win4thPrizeReward = if (isWonPrize4th == true) 40000 else 0
                win5thPrizeReward = if (isWonPrize5th == true) 20000 else 0
                winLast2DigitsPrizeReward = if(isWonLast2digitPrize == true) 2000 else 0
                winFirst3DigitsPrizeReward = if (isWonFirst3digit == true) 4000 else 0
                winLast3DigitsPrizeReward = if (isWonLast3digit == true) 4000 else 0
                totalMoneyReward = win1stPrizeReward+win2ndPrizeReward+win3rdPrizeReward+
                        win4thPrizeReward+win5thPrizeReward+winDigitsNearBy1stPrizeReward+
                        winFirst3DigitsPrizeReward+winLast2DigitsPrizeReward+winLast3DigitsPrizeReward
                lottery = userLottery
            }

            insertLottery(userLottery)

            if(mode == CHECK_SINGLE_MODE) {
                navigateToCheckResult(userLottery)
            }

            }else{
            _emptyLotteryResults.postValue(Event("กรุณาเชื่อมต่ออินเตอร์เน็ต"))
        }

        return lottery
    }

    fun navigateToCheckResult(lottery: Lottery) {
        _navigateToCheckingResultFragment.value = Event(lottery)
    }

    fun stopListeningForLotteryResult(){
        repository.stopListeningForLotteryResult()
        Timber.d("stop listener")
    }




}