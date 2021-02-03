package com.example.thailotto2021_1.repository

import androidx.lifecycle.LiveData
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.LotteryDataSource
import com.example.thailotto2021_1.data.firestore.LotteryResult

import com.example.thailotto2021_1.data.local.LotteryDao
import com.example.thailotto2021_1.data.local.LotteryLocalDataSource
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named


class MainRepository @Inject constructor(
   private val userLotteryLocalDataSource: LotteryDataSource,
   private val LotteryResultDatabase: LotteryDataSource

) : LotteryRepository {

    override suspend fun insertLottery(lottery: Lottery) {
        userLotteryLocalDataSource.insertLottery(lottery)}

    override suspend fun deleteLottery(lottery: Lottery) {
        userLotteryLocalDataSource.deleteLottery(lottery)
    }
    override fun getAllLottery() = userLotteryLocalDataSource.getAllLottery()

    override fun getAllLotteryResult(): LiveData<List<LotteryResult>> = LotteryResultDatabase.getAllLotteryResult()

    override fun stopListeningForLotteryResult() = LotteryResultDatabase.stopListeningForLotteryResult()


//    override fun getLotteryByDrawDate(querydate : Long) = userLotteryLocalDataSource.getLotteryByDrawDate(querydate)

//    override suspend fun getSingleLotteryByDrawDate(lottery: String, date: Long): Lottery {
//        return userLotteryLocalDataSource.getSingleLotteryByDrawDate(lottery,date)
//    }
//
//    override fun getAllMoneyReward() = userLotteryLocalDataSource.getAllMoneyReward()
//
//    override fun getMoneyRewardByDrawDate(querydate : Long) = userLotteryLocalDataSource.getMoneyRewardByDrawDate(querydate)




}