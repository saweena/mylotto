package com.maicakapo.thailotto2021_1.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maicakapo.thailotto2021_1.R
import com.maicakapo.thailotto2021_1.data.firestore.LotteryResult
import com.maicakapo.thailotto2021_1.databinding.FragmentShowLotteryResultBinding
import com.maicakapo.thailotto2021_1.ui.viewmodel.MainViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.chrono.ThaiBuddhistChronology
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.lang.StringBuilder
import java.util.*

class ShowLotteryResultFragment : Fragment() {

    lateinit var viewModel : MainViewModel
    lateinit var binding : FragmentShowLotteryResultBinding
    private var lotteryResult : LotteryResult? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_show_lottery_result,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        observeLotteryResultByDrawDate()

        return  binding.root
    }


    private fun observeLotteryResultByDrawDate(){
        viewModel.lotteryResultByDrawDate.observe(viewLifecycleOwner, Observer {
            lotteryResult = it
           // binding.test.text = "งวดล่าสุดจาก FM showLotto คือ ${latestLotteryResult.toString()}"

            Timber.d("งวดที่เลือก คือ ${it.toString()}")
            if(it!=null){
                setContent()
            }
        })
    }


    private fun setContent(){
        binding.ivNoData.visibility = View.GONE
        val fm = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("th","TH"))
            .withChronology(ThaiBuddhistChronology.INSTANCE)
        val t = "ผลสลากกินแบ่งรัฐบาล\nงวดวันที่ ${LocalDate.ofEpochDay(lotteryResult?.date!!).format(fm)}"
        binding.drawdate.text = t
        binding.prize1st.apply {
            setTitle("รางวัลที่ 1")
            setPrize("รางวัลละ 6,000,000 บาท")
            setContentTextSize(40f)
            setData(lotteryResult?.prize1st ?: "")
        }

        binding.prize2digit.apply {
            setTitle("เลขท้าย 2 ตัว")
            setPrize("1 รางวัลๆละ 2,000 บาท")
            setContentTextSize(30f)
            setData(lotteryResult?.prizeLast2Digit ?: "")
        }

        binding.prizeFirst3digit.apply {
            setTitle("เลขหน้า 3 ตัว")
            setPrize("2 รางวัลๆละ 4,000 บาท")
            setContentTextSize(25f)
            setData(lotteryResult?.prizeFirst3Digit.toString())
        }

        binding.prizeLast3digit.apply {
            setTitle("เลขท้าย 3 ตัว")
            setPrize("2 รางวัลๆละ 4,000 บาท")
            setContentTextSize(25f)
            setData(lotteryResult?.prizeLast3Digit.toString())

        }

        binding.prizeNearBy1st.apply {
            setTitle("รางวัลใกล้เคียงรางวัลที่ 1")
            setPrize("2 รางวัลๆละ 100,000 บาท")
            setContentTextSize(30f)
            setData(lotteryResult?.nearBy1stPrize.toString())
        }

        binding.prize2rd.apply {
            setTitle("รางวัลที่ 2")
            setPrize("5 รางวัลๆละ 200,000 บาท")

            setData(lotteryResult?.prize2nd.toString())
        }

        binding.prize3rd.apply {
            setTitle("รางวัลที่ 3")
            setPrize("10 รางวัลๆละ 80,000 บาท")
            setData(lotteryResult?.prize3rd.toString())
        }

        binding.prize4rd.apply {
            setTitle("รางวัลที่ 4")
            setPrize("50 รางวัลๆละ 40,000 บาท")
            setData(lotteryResult?.prize4th.toString())
        }

        binding.prize5rd.apply {
            setTitle("รางวัลที่ 5")
            setPrize("100 รางวัลๆละ 20,000 บาท")
            setData(lotteryResult?.prize5th.toString())
        }
    }

    private fun setTextFormat(input : List<String>?) : String{
        val sb = StringBuilder()
        if( input != null){
            for(i in input){
                sb.append("$i   ")
            }
        }

        return sb.toString()

    }


}