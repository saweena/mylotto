package com.example.thailotto2021_1.ui.fragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.databinding.ItemDateBinding
import com.example.thailotto2021_1.databinding.ItemRecyclerviewDigitBinding
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class PreviousResultAdapter : ListAdapter<LotteryResult,PreviousResultAdapter.Vholder>(LotteryResultDiffCallback()) {
    class Vholder(val binding : ItemRecyclerviewDigitBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : LotteryResult){
            binding.lotteryResult = item

        }
        companion object {
            fun from(parent: ViewGroup): Vholder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecyclerviewDigitBinding.inflate(layoutInflater,parent,false)
                return Vholder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {
        return Vholder.from(parent)
    }

    override fun onBindViewHolder(holder: Vholder, position: Int) {
        holder.bind(getItem(position))
    }

    class LotteryResultDiffCallback : DiffUtil.ItemCallback<LotteryResult>() {
        override fun areItemsTheSame(oldItem: LotteryResult, newItem: LotteryResult): Boolean {
            return oldItem.date == newItem.date
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: LotteryResult, newItem: LotteryResult): Boolean {
            return oldItem == newItem
        }
    }
}