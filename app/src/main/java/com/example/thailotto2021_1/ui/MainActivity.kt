package com.example.thailotto2021_1.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.thailotto2021_1.R
import com.example.thailotto2021_1.data.firestore.LotteryResult
import com.example.thailotto2021_1.databinding.ActivityMainBinding
import com.example.thailotto2021_1.ui.viewmodel.MainViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.CollectionReference

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import java.lang.StringBuilder
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


    }

    override fun onStop() {
        super.onStop()
        viewModel.stopListeningForLotteryResult()

    }
}