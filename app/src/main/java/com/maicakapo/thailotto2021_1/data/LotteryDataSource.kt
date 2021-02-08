package com.maicakapo.thailotto2021_1.data

import androidx.lifecycle.LiveData
import com.maicakapo.thailotto2021_1.data.firestore.LotteryResult

interface LotteryDataSource {

    suspend fun insertLottery(lottery: Lottery)
    suspend fun deleteLottery(lottery: Lottery)
    fun getAllLottery() : LiveData<List<Lottery>>
  //  fun getLotteryByDrawDate(date : Long) : LiveData<List<Lottery>>
    fun getAllLotteryResult() : LiveData<List<LotteryResult>>
    fun stopListeningForLotteryResult()





}