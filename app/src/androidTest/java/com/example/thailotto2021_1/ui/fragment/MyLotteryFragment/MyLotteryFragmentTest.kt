package com.example.thailotto2021_1.ui.fragment.MyLotteryFragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.launchFragmentInHiltContainer
import com.example.thailotto2021_1.ui.LotteryFragmentFactory
import com.example.thailotto2021_1.ui.fragment.PreviousResultFragment.PreviousResultFragment
import com.example.thailotto2021_1.ui.fragment.adapter.UserLotteryAdapter
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class MyLotteryFragmentTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: LotteryFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickUserLottery_navigateToCheckingResultFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MyLotteryFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
            val list = mutableListOf(Lottery(18659,"248097"),Lottery(18659,"248099"))
            userLotteryAdapter.addHeaderAndSubmitList(list)
//            val testViewmodel = MainViewModel()
        }

        onView(withId(R.id.rvUserLottery)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserLotteryAdapter.ViewHolder>(
                2,
                click()
            )
        )

        verify(navController).navigate(MyLotteryFragmentDirections.actionMyLotteryFragmentToCheckingResultFragment(Lottery(18659,"248099")))

    }



}