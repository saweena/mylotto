package com.maicakapo.thailotto2021_1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maicakapo.thailotto2021_1.data.Lottery
import com.maicakapo.thailotto2021_1.data.firestore.LotteryResult


class FakeRepositoryAndroidTest : LotteryRepository{

    private val userLotteryItems = mutableListOf<Lottery>()

    private val _allUserLottery = MutableLiveData<List<Lottery>>()
    val allUserLottery : LiveData<List<Lottery>> = _allUserLottery


    private val allLotteryResult = MutableLiveData<List<LotteryResult>>()

    private val lotteryResultItem = LotteryResult(date = 18644,drawNumber = "64-1",prize1st = "111111",prize2nd = "222222 222223",
            prize3rd = "333333 333334",prize4th = "444444 444445",prize5th = "555555 555556",prizeFirst3Digit = "111 222",prizeLast2Digit = "00",
            prizeLast3Digit = "333 444",nearBy1stPrize = "111110 111112")

    private var shouldReturnDatabaseError = false

    init {
        refreshLiveData()
    }
    fun setShouldReturnDatabaseError(value : Boolean){
        shouldReturnDatabaseError = value
    }

    private fun refreshLiveData(){
        _allUserLottery.postValue(userLotteryItems)
    }

    override suspend fun insertLottery(lottery: Lottery) {
        userLotteryItems.add(lottery)
        refreshLiveData()
    }

    override suspend fun deleteLottery(lottery: Lottery) {
        userLotteryItems.remove(lottery)
        refreshLiveData()
    }

    override fun getAllLottery(): LiveData<List<Lottery>> {
        return allUserLottery
    }

    override fun getAllLotteryResult(): LiveData<List<LotteryResult>> {

        if(shouldReturnDatabaseError){
            allLotteryResult.postValue(emptyList())
        }else{
            val list = listOf<LotteryResult>(lotteryResultItem)
            allLotteryResult.postValue(list)
        }
        return allLotteryResult
    }

    override fun stopListeningForLotteryResult() {
        TODO("Not yet implemented")
    }
}