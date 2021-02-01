package com.example.thailotto2021_1.repository

import androidx.lifecycle.LiveData
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.google.firebase.firestore.CollectionReference

interface LotteryRepository {

    suspend fun insertLottery(lottery: Lottery)
    suspend fun deleteLottery(lottery: Lottery)
    fun getAllLottery() : LiveData<List<Lottery>>
    fun getLotteryByDrawDate(date : Long) : LiveData<List<Lottery>>
    fun getAllMoneyReward() : LiveData<Long>
    fun getMoneyRewardByDrawDate(date : Long) : LiveData<Long>
    fun getAllLotteryResult() : CollectionReference
    suspend fun getSingleLotteryByDrawDate(lottery: String, date: Long): Lottery
}