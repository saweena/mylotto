package com.maicakapo.thailotto2021_1.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.maicakapo.thailotto2021_1.R
import com.maicakapo.thailotto2021_1.data.Lottery
import com.maicakapo.thailotto2021_1.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()
    }

//    @Test
//    fun clickCheckResult_userLotteryInsertedToDB(){
//        val testViewModel = MainViewModel(FakeRepositoryAndroidTest())
//        launchFragmentInHiltContainer<HomeFragment> {
//            viewModel = testViewModel
//            viewModel.getLotteryResultByDrawDate("17 มกราคม 2564")
//        }
//
//        onView(withId(R.id.edSingleLottery)).perform(replaceText("111111"))
//        onView(withId(R.id.spinner)).perform(click())
//        onData(anything()).atPosition(0).perform(click())
//        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("17 มกราคม 2564"))))
////        onView(withId(R.id.tvClick)).perform(click())
////
////        assertThat(testViewModel.getAllLottery.getOrAwaitValue()).contains(Lottery(18644,"111111"))
//    }

    @Test
    fun clickCheckResult_navigateTo_CheckingResultFragment(){
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        val item = Lottery(draw_date=18659, lottery="123456", amount=1)

        onView(withId(R.id.edSingleLottery)).perform(typeText("123456"))
        onView(withId(R.id.tvClick)).perform(click())

        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToCheckingResultFragment(item)
        )
    }

    @Test
    fun clickQr_navigateTo_QrCodeFragment(){
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.ivQr)).perform(click())
        verify(navController).navigate(R.id.action_homeFragment_to_qrCodeFragment)
    }


}