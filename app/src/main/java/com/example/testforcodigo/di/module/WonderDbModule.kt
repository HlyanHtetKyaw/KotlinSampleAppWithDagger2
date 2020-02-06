package com.example.testforcodigo.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.example.testforcodigo.data.database.WonderDao
import com.example.testforcodigo.data.database.WonderDatabase
import com.example.testforcodigo.data.database.WonderDatabase.Companion.DATABASE_NAME

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WonderDbModule {
    @Singleton
    @Provides
    fun provideWonderDatabase(context: Context): WonderDatabase {
        return Room.databaseBuilder(
            context,
            WonderDatabase::class.java, DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideWonderDao(wonderDatabase: WonderDatabase): WonderDao {
        return wonderDatabase.wonderDao()
    }
}