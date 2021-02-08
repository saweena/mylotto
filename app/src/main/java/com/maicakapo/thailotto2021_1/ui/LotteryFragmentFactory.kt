package com.maicakapo.thailotto2021_1.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.maicakapo.thailotto2021_1.ui.fragment.MyLotteryFragment.MyLotteryFragment
import com.maicakapo.thailotto2021_1.ui.fragment.PreviousResultFragment.PreviousResultFragment
import com.maicakapo.thailotto2021_1.ui.fragment.TrendingFragment
import com.maicakapo.thailotto2021_1.ui.fragment.adapter.PreviousResultAdapter
import com.maicakapo.thailotto2021_1.ui.fragment.adapter.TrendingImageAdapter
import com.maicakapo.thailotto2021_1.ui.fragment.adapter.UserLotteryAdapter
import javax.inject.Inject
import javax.inject.Named

class LotteryFragmentFactory @Inject constructor(
    @Named("previousResult") private val previousResultAdapter: PreviousResultAdapter,
    @Named("userLottery") private val userLotteryAdapter: UserLotteryAdapter,
    private val trendingImageAdapter: TrendingImageAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            PreviousResultFragment::class.java.name -> PreviousResultFragment(previousResultAdapter)
            MyLotteryFragment::class.java.name -> MyLotteryFragment(userLotteryAdapter)
            TrendingFragment::class.java.name -> TrendingFragment(trendingImageAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}