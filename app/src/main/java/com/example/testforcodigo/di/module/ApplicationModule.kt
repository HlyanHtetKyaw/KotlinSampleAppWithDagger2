package com.example.testforcodigo.di.module

import com.example.testforcodigo.BuildConfig.BASE_URL
import com.example.testforcodigo.data.rest.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
