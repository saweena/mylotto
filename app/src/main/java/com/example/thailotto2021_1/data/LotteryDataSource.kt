package com.example.thailotto2021_1.data

import androidx.lifecycle.LiveData
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.google.firebase.firestore.CollectionReference

interface LotteryDataSource {

    suspend fun insertLottery(lottery: Lottery)
    suspend fun deleteLottery(lottery: Lottery)
    fun getAllLottery() : LiveData<List<Lottery>>
  //  fun getLotteryByDrawDate(date : Long) : LiveData<List<Lottery>>
    fun getAllLotteryResult() : LiveData<List<LotteryResult>>
    fun stopListeningForLotteryResult()





}