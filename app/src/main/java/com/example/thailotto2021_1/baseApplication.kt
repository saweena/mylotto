package com.example.thailotto2021_1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class baseApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}