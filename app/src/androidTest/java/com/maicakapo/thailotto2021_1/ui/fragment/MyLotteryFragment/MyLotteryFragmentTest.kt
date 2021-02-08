package com.maicakapo.thailotto2021_1.ui.fragment.MyLotteryFragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.maicakapo.thailotto2021_1.R
import com.maicakapo.thailotto2021_1.data.Lottery
import com.maicakapo.thailotto2021_1.launchFragmentInHiltContainer
import com.maicakapo.thailotto2021_1.repository.FakeRepositoryAndroidTest
import com.maicakapo.thailotto2021_1.ui.LotteryFragmentFactory
import com.maicakapo.thailotto2021_1.ui.TestLotteryFragmentFactory
import com.maicakapo.thailotto2021_1.ui.fragment.adapter.UserLotteryAdapter
import com.maicakapo.thailotto2021_1.ui.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi

class MyLotteryFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: TestLotteryFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }


    @Test
    fun clickUserLottery_navigateToCheckingResultFragment(){
        val navController = mock(NavController::class.java)
        var testViewModel : MainViewModel? = null
        val l1 =Lottery(18659,"248097")
        val l2 =Lottery(18659,"248000")
        //val list = mutableListOf(Lottery(18659,"248097"),Lottery(18659,"248099"))
        launchFragmentInHiltContainer<MyLotteryFragment>(
            fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
            testViewModel = viewModel

            viewModel?.insertLottery(l1)
            viewModel?.insertLottery(l2)

        }

        onView(withId(R.id.rvUserLottery)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserLotteryAdapter.ViewHolder>(
                1,
                click()
            )
        )

        verify(navController).navigate(MyLotteryFragmentDirections.actionMyLotteryFragmentToCheckingResultFragment(l1))
    }
}