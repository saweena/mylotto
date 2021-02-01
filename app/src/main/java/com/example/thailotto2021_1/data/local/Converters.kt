package com.example.thailotto2021_1.data.local

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class Converters {

    @TypeConverter
    fun epochToThaiDate(epoch: Long?): LocalDate? {
        val fm = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
            .withChronology(ThaiBuddhistChronology.INSTANCE)
        val r = LocalDate.ofEpochDay(epoch!!)
        //r.format(fm)
        return r
    }


    @TypeConverter
    fun thaiDateToEpoch(thaidate: String?): Long? {
        val fm2 = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
        return LocalDate.parse(thaidate,fm2).toEpochDay() - 198326
    }
}