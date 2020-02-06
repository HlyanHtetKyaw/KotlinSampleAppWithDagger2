package com.example.testforcodigo.data.rest

import com.example.testforcodigo.data.model.WonderResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("13g69v")
    fun getWonders(): Single<WonderResponse>
}