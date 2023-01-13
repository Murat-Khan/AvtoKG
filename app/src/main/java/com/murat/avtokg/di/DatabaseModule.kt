package com.murat.avtokg.di

import android.content.Context
import androidx.room.Room
import com.murat.avtokg.MyApp
import com.murat.avtokg.db.CarDatabase
import com.murat.avtokg.db.CarEntity
import com.murat.avtokg.repository.DbRepository
import com.murat.avtokg.utils.Constants.CAR_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
     context,CarDatabase::class.java,CAR_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao() = provideDatabase(MyApp.context).carDao()

    @Provides
    @Singleton
    fun providesEntity() = CarEntity()

    @Provides
    @Singleton
    fun getCarRepo() : DbRepository = DbRepository(provideDao())

}