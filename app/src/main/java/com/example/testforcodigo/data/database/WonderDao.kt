package com.example.testforcodigo.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.testforcodigo.data.model.WonderDbData
import io.reactivex.Single

@Dao
interface WonderDao {
    @Query("SELECT * FROM wonder_table")
    fun getAllWonders(): Single<List<WonderDbData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wonderDbData: WonderDbData)
}