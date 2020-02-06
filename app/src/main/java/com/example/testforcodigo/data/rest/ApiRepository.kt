package com.example.testforcodigo.data.rest

import com.example.testforcodigo.data.model.WonderResponse
import io.reactivex.Single
import javax.inject.Inject

class ApiRepository @Inject
constructor(private val apiService: ApiService) {

    fun getWonders(): Single<WonderResponse> {
        return apiService.getWonders()
    }

}