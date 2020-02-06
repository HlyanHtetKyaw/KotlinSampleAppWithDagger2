package com.example.testforcodigo.data.database

import com.example.testforcodigo.data.model.WonderDbData
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WonderDbRepository @Inject
constructor(private val wonderDao: WonderDao) {

    fun getAllWonders(): Single<List<WonderDbData>> {
        return wonderDao.getAllWonders()
    }

    fun insertWonder(wonderDbData: WonderDbData) {
        wonderDao.insert(wonderDbData)
    }

}