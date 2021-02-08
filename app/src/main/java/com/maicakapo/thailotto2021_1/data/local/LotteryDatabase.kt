package com.maicakapo.thailotto2021_1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maicakapo.thailotto2021_1.data.Lottery

@Database(entities = [Lottery::class], version = 1)

abstract class LotteryDatabase : RoomDatabase() {
    abstract fun getLotteryDao() : LotteryDao
}