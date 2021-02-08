package com.maicakapo.thailotto2021_1.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.maicakapo.thailotto2021_1.databinding.FragmentTrendingBinding
import com.maicakapo.thailotto2021_1.databinding.ItemTrendingRecyclerviewBinding
import timber.log.Timber
import javax.inject.Inject

class TrendingImageAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<String, TrendingImageAdapter.TrendingVHolder>(TrendingDiffCallBack()){
    class TrendingVHolder(val binding: ItemTrendingRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    class TrendingDiffCallBack : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingVHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTrendingRecyclerviewBinding.inflate(layoutInflater,parent,false)
        return TrendingVHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingVHolder, position: Int) {
        val url = getItem(position)
//        holder.itemView.apply {
//            glide.load(url) to ivTrending
//        }
        Timber.d("url is $url in position = $position")
        glide.load(url).into(holder.binding.ivTrending)
    }
}