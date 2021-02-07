package com.example.thailotto2021_1.ui.fragment.MyLotteryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.databinding.FragmentMyLotteryBinding
import com.example.thailotto2021_1.other.EventObserver
import com.example.thailotto2021_1.ui.fragment.adapter.ITEM_VIEW_TYPE_HEADER
import com.example.thailotto2021_1.ui.fragment.adapter.UserLotteryAdapter
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import com.google.android.gms.ads.AdRequest
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named


class MyLotteryFragment @Inject constructor(@Named("userLottery") val userLotteryAdapter: UserLotteryAdapter) : Fragment() {

    lateinit var binding : FragmentMyLotteryBinding
    lateinit var viewModel : MainViewModel
    //lateinit var userLotteryAdapter: UserLotteryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_my_lottery,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerview()
        observeAllUserLottery()
        observeNavigateToWonLotteryFragment()
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        return binding.root
    }

    private fun observeAllUserLottery() {
        viewModel.getAllLottery.observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
            userLotteryAdapter.addHeaderAndSubmitList(it)
        })
    }

    private fun setupRecyclerview() {
//        adapter = UserLotteryAdapter(LotteryListener {
//            viewModel.navigateToCheckResult(it)
//        })
       // userLotteryAdapter= UserLotteryAdapter()
        binding.rvUserLottery.adapter = userLotteryAdapter
        val manager = GridLayoutManager(activity, 3)
        manager.spanSizeLookup =object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int = when(userLotteryAdapter.getItemViewType(position)){
                ITEM_VIEW_TYPE_HEADER -> 3
                else -> 1
            }
        }

        binding.rvUserLottery.layoutManager = manager
        userLotteryAdapter.setOnItemClickListener {
            viewModel.navigateToCheckResult(it.lottery)
        }
    }

    private fun observeNavigateToWonLotteryFragment() {
        viewModel.navigateToCheckingResultFragment.observe(viewLifecycleOwner, EventObserver {
            it?.let {
                findNavController().navigate(
                    MyLotteryFragmentDirections.actionMyLotteryFragmentToCheckingResultFragment(it)

                )

            }
        })
    }


}