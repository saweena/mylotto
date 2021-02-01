package com.example.thailotto2021_1.data

import androidx.lifecycle.LiveData
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.google.firebase.firestore.CollectionReference

interface LotteryDataSource {

    suspend fun insertLottery(lottery: Lottery)
    suspend fun deleteLottery(lottery: Lottery)
    fun getAllLottery() : LiveData<List<Lottery>>
    fun getLotteryByDrawDate(date : Long) : LiveData<List<Lottery>>
    fun getAllMoneyReward() : LiveData<Long>
    fun getMoneyRewardByDrawDate(date : Long) : LiveData<Long>
    fun getAllLotteryResult() : CollectionReference
    suspend fun getSingleLotteryByDrawDate(lottery : String, date : Long) : Lottery




}