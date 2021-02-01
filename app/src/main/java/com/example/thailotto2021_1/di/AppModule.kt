package com.example.thailotto2021_1.di

import android.content.Context
import androidx.room.Room
import com.example.thailotto2021_1.data.LotteryDataSource
import com.example.thailotto2021_1.data.local.LotteryDao
import com.example.thailotto2021_1.data.local.LotteryDatabase
import com.example.thailotto2021_1.data.local.LotteryLocalDataSource

import com.example.thailotto2021_1.other.Constants
import com.example.thailotto2021_1.other.Constants.USER_LOTTERY_DB
import com.example.thailotto2021_1.repository.LotteryRepository
import com.example.thailotto2021_1.repository.MainRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLotteryDatabase(
        @ApplicationContext app : Context
    )= Room.databaseBuilder(
        app,
        LotteryDatabase::class.java,
        USER_LOTTERY_DB)
        .build()

    @Singleton
    @Provides
    fun provideLotteryDao(db : LotteryDatabase) = db.getLotteryDao()

    @Singleton
    @Provides
    fun provideFirebaseCollection() = FirebaseFirestore.getInstance().collection(Constants.REWARD_LOTTERY_FIRESTORE)

    @Singleton
    @Provides
    fun provideUserLotteryLocalDatasource(dao : LotteryDao) = LotteryLocalDataSource(dao) as LotteryDataSource

    @Singleton
    @Provides
    fun provideDefaultMainRepository(
            userLotteryLocalDataSource: LotteryDataSource,
            firestore: CollectionReference
    ) = MainRepository(userLotteryLocalDataSource, firestore) as LotteryRepository
}