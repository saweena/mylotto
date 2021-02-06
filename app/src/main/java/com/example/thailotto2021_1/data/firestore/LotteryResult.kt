package com.example.thailotto2021_1.data.firestore

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
data class LotteryResult(
    val date : Long = 0,
    val drawNumber : String = "",
    val prize1st  : String ="",
    val prizeFirst3Digit : String ="",
    val prizeLast3Digit : String ="",
    val prizeLast2Digit : String ="",
    val nearBy1stPrize : String = "",
    val prize2nd  : String ="",
    val prize3rd  : String ="",
    val prize4th  : String ="",
    val prize5th  : String =""

)