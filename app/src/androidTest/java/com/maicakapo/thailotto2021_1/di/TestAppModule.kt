package com.maicakapo.thailotto2021_1.di

import android.content.Context
import androidx.room.Room
import com.maicakapo.thailotto2021_1.data.local.LotteryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Named("lottery_db")
    @Provides
    fun provideInMemoryDB(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,LotteryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}