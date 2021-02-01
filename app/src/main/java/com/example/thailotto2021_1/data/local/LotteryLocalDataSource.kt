package com.example.thailotto2021_1.data.local

import androidx.lifecycle.LiveData
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.LotteryDataSource
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LotteryLocalDataSource @Inject constructor(
    private val lotteryDao: LotteryDao
) : LotteryDataSource{

    override suspend fun insertLottery(lottery: Lottery) = withContext(Dispatchers.IO){
        lotteryDao.insertLottery(lottery)
    }

    override suspend fun deleteLottery(lottery: Lottery)= withContext(Dispatchers.IO){
        lotteryDao.deleteLottery(lottery)
    }

    override fun getAllLottery(): LiveData<List<Lottery>> {
        return lotteryDao.getAllLottery()
    }

    override fun getLotteryByDrawDate(querydate: Long): LiveData<List<Lottery>> {
        return lotteryDao.getLotteryByDrawDate(querydate)
    }

    override fun getAllMoneyReward(): LiveData<Long> {
        return lotteryDao.getAllMoneyReward()
    }

    override fun getMoneyRewardByDrawDate(querydate: Long): LiveData<Long> {
        return lotteryDao.getMoneyRewardByDrawDate(querydate)
    }

    override suspend fun getSingleLotteryByDrawDate(lottery: String, date: Long): Lottery {
        return lotteryDao.getSingleLotteryByDrawDate(lottery,date)
    }


    override fun getAllLotteryResult(): CollectionReference {
        TODO("Not yet implemented")
    }


}