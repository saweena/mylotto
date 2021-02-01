package com.example.thailotto2021_1.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.repository.LotteryRepository
import com.google.firebase.firestore.MetadataChanges
import timber.log.Timber


class MainViewModel @ViewModelInject constructor(
    private val repository: LotteryRepository
) : ViewModel() {


}