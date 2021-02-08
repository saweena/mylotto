package com.maicakapo.thailotto2021_1.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.maicakapo.thailotto2021_1.repository.LotteryRepository


class MainViewModel @ViewModelInject constructor(
    private val repository: LotteryRepository
) : ViewModel() {


}