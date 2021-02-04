package com.example.thailotto2021_1.ui.fragment.PreviousResultFragment

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.filters.MediumTest
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.launchFragmentInHiltContainer
import com.example.thailotto2021_1.ui.LotteryFragmentFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class PreviousResultFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var fragmentFactory: LotteryFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

//    @Test
//    fun showCorrectResult(){
//        val navController = mock(NavController::class.java)
//        launchFragmentInHiltContainer<PreviousResultFragment>(fragmentFactory = fragmentFactory) {
//            Navigation.setViewNavController(requireView(),navController)
//            val list = mutableListOf(LotteryResult(1,"64","111111"))
//            previousResultAdapter.submitList(list)
//        }
//
//
//    }
}