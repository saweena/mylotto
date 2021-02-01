package com.example.thailotto2021_1.data.firestore

import androidx.lifecycle.LiveData
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.LotteryDataSource
import com.example.thailotto2021_1.data.local.LotteryLocalDataSource
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class RewardLotteryDatabase @Inject constructor(
    private val lotteryResultCollection : CollectionReference)
    : LotteryDataSource {

    override fun getAllLotteryResult() : CollectionReference {
        return lotteryResultCollection

    }
    override suspend fun insertLottery(lottery: Lottery) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLottery(lottery: Lottery) {
        TODO("Not yet implemented")
    }

    override fun getAllLottery(): LiveData<List<Lottery>> {
        TODO("Not yet implemented")
    }

    override fun getLotteryByDrawDate(querydate: Long): LiveData<List<Lottery>> {
        TODO("Not yet implemented")
    }

    override fun getAllMoneyReward(): LiveData<Long> {
        TODO("Not yet implemented")
    }

    override fun getMoneyRewardByDrawDate(querydate: Long): LiveData<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleLotteryByDrawDate(lottery: String, date: Long):Lottery {
        TODO("Not yet implemented")
    }

}