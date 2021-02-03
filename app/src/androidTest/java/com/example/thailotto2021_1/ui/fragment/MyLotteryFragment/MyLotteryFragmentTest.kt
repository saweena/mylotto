package com.example.thailotto2021_1.ui.fragment.MyLotteryFragment

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.filters.MediumTest
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.launchFragmentInHiltContainer
import com.example.thailotto2021_1.ui.LotteryFragmentFactory
import com.example.thailotto2021_1.ui.fragment.PreviousResultFragment.PreviousResultFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class MyLotteryFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var fragmentFactory: LotteryFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }


}