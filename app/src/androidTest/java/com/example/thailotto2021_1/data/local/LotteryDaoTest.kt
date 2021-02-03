package com.example.thailotto2021_1.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.thailotto2021_1.data.Lottery
import com.example.thailotto2021_1.getOrAwaitValue
import com.example.thailotto2021_1.launchFragmentInHiltContainer
import com.example.thailotto2021_1.ui.fragment.HomeFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class LotteryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule =HiltAndroidRule(this)

    @Inject
    lateinit var database: LotteryDatabase
    private lateinit var dao: LotteryDao

    @Before
    @Named("lottery_db")
    fun setup(){
        hiltRule.inject()
        dao = database.getLotteryDao()
    }

    @After
    fun teardown(){
        database.close()
    }



    @Test
    fun insertLottery() = runBlockingTest {
        val lotteryItem = Lottery(18123,"123456")
        dao.insertLottery(lotteryItem)

        val allLotteryList = dao.getAllLottery().getOrAwaitValue()
        assertThat(allLotteryList).contains(lotteryItem)
    }

    @Test
    fun deleteLottery() = runBlockingTest {
        val lotteryItem = Lottery(18123,"123456")
        dao.insertLottery(lotteryItem)
        dao.deleteLottery(lotteryItem)

        val allLotteryList = dao.getAllLottery().getOrAwaitValue()
        assertThat(allLotteryList).doesNotContain(lotteryItem)
    }
}