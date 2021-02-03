package com.example.thailotto2021_1.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.databinding.FragmentHomeBinding
import com.example.thailotto2021_1.other.EventObserver
import com.example.thailotto2021_1.other.Status
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment() {

    lateinit var viewModel : MainViewModel
    lateinit var binding : FragmentHomeBinding
    private var lotteryResultByDrawDate : LotteryResult? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_home,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        observeLotteryResultByDrawDate()
        observeDrawDateListForSpinner()
        observeNavigateCheckingResultFragment()
        observeInsertStatus()

        binding.tvClick.setOnClickListener {

            val input = edSingleLottery.text.toString()
            viewModel.checkLottery(input)

        }
        binding.ivQr.setOnClickListener {
           findNavController().navigate(R.id.action_homeFragment_to_qrCodeFragment)
        }

        binding.edSingleLottery.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when(actionId){
                EditorInfo.IME_ACTION_SEARCH ->{
                    if (v.text.length!=6){
                        Toast.makeText(requireContext(),"กรุณาใส่เลข 6 หลัก",Toast.LENGTH_LONG).show()
                        true
                    }else{
                        viewModel.checkLottery(v.text.toString())
                        hideSoftKeyboard(requireActivity())
                        true
                    }
                }else -> false
            }
        }
        //val tbd = ThaiBuddhistDate.of(2563,12,30)
//        val fm = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
//                .withChronology(ThaiBuddhistChronology.INSTANCE)
//        val r = LocalDate.ofEpochDay(18626)
//        val t =r.format(fm)
//
//        val fm2 = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
//        val e = LocalDate.parse(t,fm2).toEpochDay() - 198326
//        Timber.d("$t to ${e.toString()}")

        return binding.root

    }

//    private fun observeAllRewardedLottery(){
//        viewModel.allLotteryResult.observe(viewLifecycleOwner, Observer {
//
//        })
//    }

    private fun observeDrawDateListForSpinner(){
        viewModel.listOfSpinner.observe(viewLifecycleOwner, Observer {
            setSpinner(it)
        })
    }

    private fun setSpinner(list : MutableList<String>) {
        ArrayAdapter(requireContext(),R.layout.row_spinner,list).also {
                adapter ->
            adapter.setDropDownViewResource(R.layout.row_spinners_dropdown)

            binding.spinner.adapter = adapter
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.getLotteryResultByDrawDate(list[position])

                }

            }
        }
    }

    private fun observeLotteryResultByDrawDate(){
        viewModel.lotteryResultByDrawDate.observe(viewLifecycleOwner, Observer {
            lotteryResultByDrawDate = it

        })
    }

    private fun observeNavigateCheckingResultFragment() {
        viewModel.navigateToCheckingResultFragment.observe(viewLifecycleOwner, EventObserver {
            it?.let {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCheckingResultFragment(it))
            }
        })
    }

    private fun hideSoftKeyboard(activity: FragmentActivity) {
        if (activity.currentFocus == null){
            return
        }
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    private fun observeInsertStatus(){
        viewModel.insertStatus.observe(viewLifecycleOwner,EventObserver{
            when(it.status){
                Status.ERROR -> Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                else -> return@EventObserver
            }
        })
    }










}