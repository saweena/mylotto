package com.example.thailotto2021_1.data.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.LotteryDataSource
import com.example.thailotto2021_1.data.local.LotteryLocalDataSource
import com.example.thailotto2021_1.other.Constants
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject


class LotteryResultDatabase (private val lotteryResultFirestore : CollectionReference)
    : LotteryDataSource {

    private lateinit var lotteryResultRegistration: ListenerRegistration
    private val _lotteryResultList = MutableLiveData<List<LotteryResult>>()

    override fun getAllLotteryResult() : LiveData<List<LotteryResult>> {
        var list = mutableListOf<LotteryResult>()
        lotteryResultRegistration = lotteryResultFirestore.orderBy("date",Query.Direction.DESCENDING)
                .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
                    if(error!=null){
                        Timber.d(error.message)
                        return@addSnapshotListener _lotteryResultList.postValue(emptyList())
                    }

                    list = value!!.toObjects(LotteryResult::class.java)
                    _lotteryResultList.postValue(list)

                }
        return _lotteryResultList
    }

    override fun stopListeningForLotteryResult() = lotteryResultRegistration.remove()


    override suspend fun insertLottery(lottery: Lottery) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLottery(lottery: Lottery) {
        TODO("Not yet implemented")
    }

    override fun getAllLottery(): LiveData<List<Lottery>> {
        TODO("Not yet implemented")
    }

//    override fun getLotteryByDrawDate(querydate: Long): LiveData<List<Lottery>> {
//        TODO("Not yet implemented")
//    }

//    override fun getAllMoneyReward(): LiveData<Long> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getMoneyRewardByDrawDate(querydate: Long): LiveData<Long> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getSingleLotteryByDrawDate(lottery: String, date: Long):Lottery {
//        TODO("Not yet implemented")
//    }

}