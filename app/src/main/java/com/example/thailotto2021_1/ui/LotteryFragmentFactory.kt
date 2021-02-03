package com.example.thailotto2021_1.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.thailotto2021_1.ui.fragment.PreviousResultFragment.PreviousResultFragment
import com.example.thailotto2021_1.ui.fragment.adapter.PreviousResultAdapter
import javax.inject.Inject

class LotteryFragmentFactory @Inject constructor(
    private val previousResultAdapter: PreviousResultAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            PreviousResultFragment::class.java.name -> PreviousResultFragment(previousResultAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}