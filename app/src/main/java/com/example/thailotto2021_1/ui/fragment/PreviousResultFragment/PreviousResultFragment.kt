package com.example.thailotto2021_1.ui.fragment.PreviousResultFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.databinding.FragmentPreviousResultBinding
import com.example.thailotto2021_1.ui.fragment.adapter.PreviousResultAdapter
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import javax.inject.Inject


class PreviousResultFragment @Inject constructor(val previousResultAdapter : PreviousResultAdapter) : Fragment() {

    lateinit var viewModel : MainViewModel
    lateinit var binding : FragmentPreviousResultBinding
   // lateinit var adapter: PreviousResultAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_previous_result,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        //adapter = PreviousResultAdapter()

        binding.rvPreviousResult.adapter = previousResultAdapter
        binding.rvPreviousResult.layoutManager = LinearLayoutManager(requireContext())

        observeAllLotteryResult()
        return binding.root
    }

    private fun observeAllLotteryResult(){
        viewModel.allLotteryResult.observe(viewLifecycleOwner, Observer {
            previousResultAdapter.submitList(it)
        })
    }


}