package com.example.thailotto2021_1.repository

import androidx.lifecycle.MutableLiveData
import com.example.thailotto2021_1.data.Lottery

class FakeRepository : LotteryRepository{

    private val userLotteryItems = mutableListOf<Lottery>()
    private val getAlluserLottery = MutableLiveData<List<Lottery>>(userLotteryItems)

    private var shouldReturnDatabaseError = false

    fun setShouldReturnDatabaseError(boolean: Boolean){
        shouldReturnDatabaseError = boolean
    }
}