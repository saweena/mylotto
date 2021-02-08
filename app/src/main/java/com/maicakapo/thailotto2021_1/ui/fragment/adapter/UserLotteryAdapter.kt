package com.maicakapo.thailotto2021_1.ui.fragment.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maicakapo.thailotto2021_1.data.Lottery
import com.maicakapo.thailotto2021_1.databinding.ItemDateBinding
import com.maicakapo.thailotto2021_1.databinding.ItemUserLotteryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.util.*


sealed class ViewItem () {
    abstract val date : Long
    abstract val userLottery : String
    class DateItem(val drawDate: Long): ViewItem(){
        override val date: Long = drawDate
        override val userLottery: String =""
   }
    class LotteryItem(val lottery : Lottery): ViewItem(){
        override val date: Long = lottery.draw_date
        override val userLottery: String = lottery.lottery
    }

}

const val ITEM_VIEW_TYPE_HEADER = 0
const val ITEM_VIEW_TYPE_ITEM = 1

class UserLotteryAdapter ()
    : ListAdapter<ViewItem, RecyclerView.ViewHolder>(LotteryDiffCallback()){



    fun addHeaderAndSubmitList(list: List<Lottery>?) {
        CoroutineScope(Dispatchers.Default).launch {
            when (list!!.size) {
                0 -> {
                    withContext(Dispatchers.Main) {
                        submitList(emptyList())
                    }
                    return@launch
                }
                1 -> {
                    //val item = listOf(ViewItem.DateItem(list[0].draw_date))+ listOf<Lottery>(list[0])
                    var item : MutableList<ViewItem> = mutableListOf()
                    //= mutableListOf(ViewItem.DateItem(list[0].draw_date)) + mutableListOf<Lottery>(list[0])
                    item.add(ViewItem.DateItem(list[0].draw_date))
                    item.add(ViewItem.LotteryItem(list[0]))
                    withContext(Dispatchers.Main) {
                        submitList(item)
                    }
                    Timber.d("Size =1 ,,,,,, ${item.toString()}")
                    return@launch
                }
                else -> {

                    var items : MutableList<ViewItem> = mutableListOf()
                    //= mutableListOf(ViewItem.DateItem(list[0].draw_date)) + mutableListOf<Lottery>(list[0])
                    items.add(ViewItem.DateItem(list[0].draw_date))
                    items.add(ViewItem.LotteryItem(list[0]))
                    var lastDate = list[0].draw_date
                    for(i in 1 until list!!.size){
                        if(list[i].draw_date!=lastDate) {
                            lastDate=list[i].draw_date
                            items.add(ViewItem.DateItem(list[i].draw_date))
                        }
                        items.add(ViewItem.LotteryItem(list[i]))
                    }
                    withContext(Dispatchers.Main) {
                        submitList(items)
                    }
//                    val test = StringBuilder()
//                    for (item in items){
//                        when(item){
//                            is ViewItem.LotteryItem -> test.append("${item.lottery.lottery} ")
//                            is ViewItem.DateItem -> test.append("Date : ${item.drawDate} ||")
//                        }
//                    }
//                    Timber.d(test.toString())

                    }
                }

            }


//            val items = when (list) {
//                null -> listOf(DataItem.Header)
//                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
//            }
//            withContext(Dispatchers.Main) {
//                submitList(items)
//            }
        }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ViewItem.DateItem -> ITEM_VIEW_TYPE_HEADER
            is ViewItem.LotteryItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderDateViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }
    private var onItemClickListener: ((ViewItem.LotteryItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ViewItem.LotteryItem) -> Unit) {
        onItemClickListener = listener
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val lotteryItem = getItem(position) as ViewItem.LotteryItem
                holder.bind(lotteryItem.lottery)
                holder.itemView.setOnClickListener {
                    onItemClickListener?.let {click->
                        click(lotteryItem)
                    }
                }


            }
            is HeaderDateViewHolder ->{
                val headerItem = getItem(position) as ViewItem.DateItem
                holder.bind(headerItem.drawDate)
            }
        }
    }

    class HeaderDateViewHolder(val binding: ItemDateBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(date : Long){
            val fm = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
                .withChronology(ThaiBuddhistChronology.INSTANCE)

            binding.dateItem.text = LocalDate.ofEpochDay(date).format(fm)
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): HeaderDateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDateBinding.inflate(layoutInflater,parent,false)
                return HeaderDateViewHolder(binding)
            }
        }
    }
    class ViewHolder(val binding: ItemUserLotteryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : Lottery){
            binding.tvUserLottery.text = item.lottery
//            binding.userLottery =item
//            binding.clickListener = clickListener
            if(item.totalMoneyReward==0L){
                binding.lotteryCard.setCardBackgroundColor(Color.GRAY)
                binding.tvLotteryAmount.visibility = View.GONE
            }
            val t = "x${item.amount}"
            binding.tvLotteryAmount.text = t
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserLotteryBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

    }
    class LotteryDiffCallback : DiffUtil.ItemCallback<ViewItem>() {
        override fun areItemsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
            return (oldItem.date == newItem.date) && (oldItem.userLottery == newItem.userLottery)
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
            return oldItem == newItem
        }
    }

}

//class LotteryListener(val clickListener: (selectedLottery: Lottery) -> Unit) {
//    fun onClick(userLottery: Lottery) = clickListener(userLottery)
//}



