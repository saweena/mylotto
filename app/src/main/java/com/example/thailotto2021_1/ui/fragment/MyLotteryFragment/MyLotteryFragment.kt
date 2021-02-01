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
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.databinding.FragmentMyLotteryBinding
import com.example.thailotto2021_1.other.EventObserver
import com.example.thailotto2021_1.ui.fragment.adapter.ITEM_VIEW_TYPE_HEADER
import com.example.thailotto2021_1.ui.fragment.adapter.LotteryListener
import com.example.thailotto2021_1.ui.fragment.adapter.UserLotteryAdapter
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import timber.log.Timber



class MyLotteryFragment : Fragment() {

    lateinit var binding : FragmentMyLotteryBinding
    lateinit var viewModel : MainViewModel
    lateinit var adapter: UserLotteryAdapter
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

        return binding.root
    }

    private fun observeAllUserLottery() {
        viewModel.getAllLottery.observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
            adapter.addHeaderAndSubmitList(it)
        })
    }

    private fun setupRecyclerview() {
        adapter = UserLotteryAdapter(LotteryListener {
            viewModel.navigateToCheckResult(it)
        })
        binding.rvUserLottery.adapter = adapter
        val manager = GridLayoutManager(activity, 3)
        manager.spanSizeLookup =object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int = when(adapter.getItemViewType(position)){
                ITEM_VIEW_TYPE_HEADER -> 3
                else -> 1
            }
        }
        binding.rvUserLottery.layoutManager = manager
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