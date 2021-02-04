package com.example.thailotto2021_1.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.databinding.FragmentTrendingBinding
import com.example.thailotto2021_1.databinding.FragmentWonLotteryBinding
import com.example.thailotto2021_1.ui.fragment.adapter.TrendingImageAdapter
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import com.google.android.gms.ads.AdRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class TrendingFragment @Inject constructor(val trendingAdapter : TrendingImageAdapter) : Fragment() {

    lateinit var binding : FragmentTrendingBinding
    lateinit var viewModel : MainViewModel
    lateinit var firebaseStorage :StorageReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_trending, container, false)
        binding.rvTrending.adapter = trendingAdapter
        binding.rvTrending.layoutManager = LinearLayoutManager(requireContext())
        firebaseStorage = Firebase.storage.reference


        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val a = firebaseStorage.child("images/").listAll().await()
                Timber.d("ListResult is $a")
                val imageUrls = mutableListOf<String>()
                for(image in a.items){
                    val url = image.downloadUrl.await()
                    imageUrls.add(url.toString())
                }
                Timber.d("imageUrls = $imageUrls")
                withContext(Dispatchers.Main){
                    trendingAdapter.submitList(imageUrls)
                }

            }catch (e : java.lang.Exception){
//                Toast.makeText(requireContext(),"กรุณาต่ออินเตอร์เน็ต",Toast.LENGTH_LONG).show()
            }


        }
        Timber.d("cloud storage = ${firebaseStorage.child("images/").listAll()} ")
        return binding.root
    }


}