package com.maicakapo.thailotto2021_1.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maicakapo.thailotto2021_1.MainCoroutineRule
import com.maicakapo.thailotto2021_1.data.Lottery
import com.maicakapo.thailotto2021_1.data.firestore.LotteryResult
import com.maicakapo.thailotto2021_1.getOrAwaitValueTest
import com.maicakapo.thailotto2021_1.other.Status
import com.maicakapo.thailotto2021_1.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel
    // Use a fake repository to be injected into the viewmodel
    private lateinit var repository: FakeRepository

    @Before
    fun setup(){
        repository = FakeRepository()
        viewModel = MainViewModel(repository)
    }
    @Test
    fun `checking user lotteryItem with empty field return Error`(){

        viewModel.checkLottery("")
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `checking user lotteryItem with more than 6 digits return Error`(){

        viewModel.checkLottery("1234112")
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert user lotteryItem with 6 digits return Success`(){

        viewModel.insertLottery(Lottery(1,"123456"))
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `Lottery result is Error return emptyList`(){
        repository.setShouldReturnDatabaseError(true)
        viewModel = MainViewModel(repository)

        val list = viewModel.allLotteryResult.getOrAwaitValueTest()
        assertThat(list).isEmpty()
    }

    @Test
    fun `Lottery result is Success return LotteryResult`(){

        val list = viewModel.allLotteryResult.getOrAwaitValueTest()
        assertThat(list).contains(
            LotteryResult(date = 1L,drawNumber = "64-1",prize1st = "111111",prize2nd = "222222 222223",
            prize3rd = "333333 333334",prize4th = "444444 444445",prize5th = "555555 555556",prizeFirst3Digit = "111 222",prizeLast2Digit = "00",
            prizeLast3Digit = "333 444",nearBy1stPrize = "111110 111112")
        )
    }

}