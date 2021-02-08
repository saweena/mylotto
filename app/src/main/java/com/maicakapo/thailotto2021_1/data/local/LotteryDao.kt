package com.maicakapo.thailotto2021_1.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maicakapo.thailotto2021_1.data.Lottery

@Dao
interface LotteryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLottery(userLottery: Lottery)

    @Delete
    suspend fun deleteLottery(userLottery: Lottery)

    @Query("SELECT * FROM user_lottery_table ORDER BY draw_date DESC")
    fun getAllLottery() : LiveData<List<Lottery>>

    @Query("SELECT * FROM user_lottery_table WHERE draw_date = :querydate")
    fun getLotteryByDrawDate(querydate : Long) : LiveData<List<Lottery>>

    @Query("SELECT * FROM user_lottery_table WHERE draw_date = :querydate AND lottery = :lottery")
    suspend fun getSingleLotteryByDrawDate(lottery : String, querydate : Long) : Lottery

    @Query("SELECT SUM(totalMoneyReward) FROM user_lottery_table")
    fun getAllMoneyReward() : LiveData<Long>

    @Query("SELECT SUM(totalMoneyReward) FROM user_lottery_table WHERE draw_date = :querydate")
    fun getMoneyRewardByDrawDate(querydate : Long) : LiveData<Long>
}