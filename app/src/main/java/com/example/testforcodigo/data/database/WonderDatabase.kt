package com.example.testforcodigo.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.testforcodigo.data.model.WonderDbData

@Database(entities = [WonderDbData::class], version = 1, exportSchema = false)
abstract class WonderDatabase : RoomDatabase() {

    abstract fun wonderDao(): WonderDao

    companion object {
        const val DATABASE_NAME = "wonder.db"
    }
}