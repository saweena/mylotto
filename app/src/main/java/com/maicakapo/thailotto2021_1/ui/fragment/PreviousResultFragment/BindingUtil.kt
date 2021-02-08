package com.maicakapo.thailotto2021_1.ui.fragment.PreviousResultFragment

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.maicakapo.thailotto2021_1.data.firestore.LotteryResult
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

@BindingAdapter("dateFormat")
fun TextView.setDate(item: LotteryResult?) {
    val fm = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
        .withChronology(ThaiBuddhistChronology.INSTANCE)
    item?.let {
        text = LocalDate.ofEpochDay(item.date).format(fm)
    }
}