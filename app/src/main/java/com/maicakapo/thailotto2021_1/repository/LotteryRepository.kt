package com.maicakapo.thailotto2021_1.repository

import androidx.lifecycle.LiveData
import com.maicakapo.thailotto2021_1.data.Lottery
import com.maicakapo.thailotto2021_1.data.firestore.LotteryResult

interface LotteryRepository {

    suspend fun insertLottery(lottery: Lottery)
    suspend fun deleteLottery(lottery: Lottery)
    fun getAllLottery() : LiveData<List<Lottery>>
    //fun getLotteryByDrawDate(date : Long) : LiveData<List<Lottery>>
    fun getAllLotteryResult() : LiveData<List<LotteryResult>>
    fun stopListeningForLotteryResult()
   // suspend fun getSingleLotteryByDrawDate(lottery: String, date: Long): Lottery
}