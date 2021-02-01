package com.example.thailotto2021_1.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.databinding.FragmentWonLotteryBinding
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_won_lottery.*
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.util.*

class CheckingResultFragment : DialogFragment() {

    lateinit var binding : FragmentWonLotteryBinding
    lateinit var viewModel : MainViewModel
    private val args: CheckingResultFragmentArgs by navArgs()
   // lateinit var userLottery : Lottery

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_won_lottery,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val userLottery  = args.userLottery
        binding.ivMoney.visibility = INVISIBLE
        binding.linearAmount.visibility = GONE

        binding.ivAmountConfirm.setOnClickListener {
            
            if(binding.edAmount.length()!=0){
                updateAmount(userLottery)
            }else{
                Toast.makeText(requireContext(),"กรุณากรอกจำนวนสลาก",Toast.LENGTH_LONG).show()
            }
        }

        binding.edAmount.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when(actionId){
                EditorInfo.IME_ACTION_DONE ->{
                    if(binding.edAmount.length()!=0){
                        updateAmount(userLottery)
                        true
                    }else{
                        Toast.makeText(requireContext(),"กรุณากรอกจำนวนสลาก",Toast.LENGTH_LONG).show()
                        true
                    }
                }else -> false
            }
        }

        setContent(userLottery)
        return binding.root
    }

    private fun setContent(userLottery: Lottery) {

        val fm = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
            .withChronology(ThaiBuddhistChronology.INSTANCE)

        binding.tvSumReward.visibility= VISIBLE
        binding.tvDrawDate.text = LocalDate.ofEpochDay(userLottery.draw_date).format(fm)
        binding.tvLottery.text=userLottery.lottery
        if(userLottery.totalMoneyReward==0L){
            binding.tvSumReward.visibility= INVISIBLE
            binding.tvResult.text = "เสียใจด้วย"
            return
        }
        val sb = StringBuilder()
        if(userLottery.win1stPrizeReward!=0L){
            sb.append("ถูกรางวัลที่ 1   6,000,000 บาท\n")
        }
        if(userLottery.winDigitsNearBy1stPrizeReward!=0L){
            sb.append("ถูกรางวัลข้างเคียงรางวัลที่ 1   100,000 บาท\n")
        }
        if(userLottery.win2ndPrizeReward!=0L){
            sb.append("ถูกรางวัลที่ 2   200,000 บาท\n")
        }
        if(userLottery.win3rdPrizeReward!=0L){
            sb.append("ถูกรางวัลที่ 3   80,000 บาท\n")
        }
        if(userLottery.win4thPrizeReward!=0L){
            sb.append("ถูกรางวัลที่ 4   40,000 บาท\n")
        }
        if(userLottery.win5thPrizeReward!=0L){
            sb.append("ถูกรางวัลที่ 5   20,000 บาท\n")
        }
        if(userLottery.winFirst3DigitsPrizeReward!=0L){
            sb.append("ถูกเลขหน้า 3 ตัว   4,000 บาท\n")
        }
        if(userLottery.winLast3DigitsPrizeReward!=0L){
            sb.append("ถูกเลขท้าย 3 ตัว   4,000 บาท\n")
        }
        if(userLottery.winLast2DigitsPrizeReward!=0L){
            sb.append("ถูกเลขท้าย 2 ตัว   2,000 บาท\n")
        }

        if(userLottery.totalMoneyReward!=0L){
            binding.ivMoney.visibility = VISIBLE
            Glide.with(this).load(R.drawable.money).into(binding.ivMoney)
            binding.linearAmount.visibility = VISIBLE
            setEditTextAmount(userLottery)

        }

        binding.tvResult.text = sb.toString()

        val total = userLottery.totalMoneyReward*userLottery.amount
        val decim = DecimalFormat("#,###")

        val text = "รวมทั้งสิ้น ${decim.format(total)} บาท"
        binding.tvSumReward.text = text

    }

    private fun setEditTextAmount(userLottery: Lottery) {
        binding.edAmount.setText(userLottery.amount.toString())
        hideSoftKeyboard(requireActivity())
        binding.edAmount.clearFocus()
    }

    private fun updateAmount(userLottery: Lottery) {
        userLottery.amount = binding.edAmount.text.toString().toInt()

        viewModel.insertLottery(userLottery)
        setContent(userLottery)


    }

    private fun hideSoftKeyboard(activity:FragmentActivity) {
        if (activity.currentFocus == null){
            return
        }
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    private fun pressEnterFromKeyboard(){
        edAmount.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){

            }
            false
        }
    }





}