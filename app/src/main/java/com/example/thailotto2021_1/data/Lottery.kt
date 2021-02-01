package com.example.thailotto2021_1.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Struct

@Entity(tableName = "user_lottery_table",primaryKeys = ["draw_date", "lottery"])
@Parcelize
data class Lottery(
    var draw_date : Long,
    var lottery : String,
    var amount : Int=1

) : Parcelable {

    var isCheck : Boolean = false
    var win1stPrizeReward : Long = 0
    var winFirst3DigitsPrizeReward : Long = 0
    var winLast3DigitsPrizeReward : Long= 0
    var winLast2DigitsPrizeReward : Long= 0
    var winDigitsNearBy1stPrizeReward : Long = 0
    var win2ndPrizeReward  : Long = 0
    var win3rdPrizeReward  : Long = 0
    var win4thPrizeReward  : Long = 0
    var win5thPrizeReward  : Long = 0
    var totalMoneyReward : Long = 0
}