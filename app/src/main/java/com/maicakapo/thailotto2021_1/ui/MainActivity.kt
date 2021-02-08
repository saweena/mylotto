package com.maicakapo.thailotto2021_1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.maicakapo.thailotto2021_1.R
import com.maicakapo.thailotto2021_1.databinding.ActivityMainBinding
import com.maicakapo.thailotto2021_1.ui.viewmodel.MainViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var lotteryFragmentFactory: LotteryFragmentFactory

    lateinit var binding : ActivityMainBinding
    lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = lotteryFragmentFactory
        setTheme(R.style.Theme_ThaiLotto2021_1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)



    }

    override fun onStop() {
        super.onStop()
        viewModel.stopListeningForLotteryResult()

    }
}