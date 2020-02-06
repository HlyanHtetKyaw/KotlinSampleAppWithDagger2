package com.example.testforcodigo.ui.main

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.testforcodigo.data.database.WonderDbRepository
import com.example.testforcodigo.data.model.Wonder
import com.example.testforcodigo.data.model.WonderDbData
import com.example.testforcodigo.data.model.WonderResponse
import com.example.testforcodigo.data.rest.ApiRepository
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class WonderListViewModel @Inject
constructor(
    application: Application,
    private val apiRepository: ApiRepository,
    private val wonderDbRepository: WonderDbRepository
) : ViewModel() {
    private var disposable: CompositeDisposable? = null

    private val wonders = MutableLiveData<List<WonderDbData>>()
    private val dbWonders = MutableLiveData<List<WonderDbData>>()
    private val wonderLoadError = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()

    private val wonderDbDataList = ArrayList<WonderDbData>()

    internal val allwondersFromDB: LiveData<List<WonderDbData>>
        get() = dbWonders

    internal val error: LiveData<Boolean>
        get() = wonderLoadError

    init {
        disposable = CompositeDisposable()
    }

    internal fun getWonders(): LiveData<List<WonderDbData>> {
        return wonders
    }

    internal fun getLoading(): LiveData<Boolean> {
        return loading
    }

    fun fetchWonders() {
        loading.value = true
        disposable!!.add(
            apiRepository.getWonders().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                    DisposableSingleObserver<WonderResponse>() {
                    override fun onSuccess(value: WonderResponse) {
                        Log.d(TAG, "onSuccess: Response" + Gson().toJson(value))
                        wonderLoadError.value = false
                        for (response: Wonder in value.wonders!!) {
                            val data = WonderDbData()
                            // data.setId(response.getId())
                            data.description = response.description
                            data.image = response.image
                            data.location = response.location
                            data.lat = response.lat
                            data.long = response.long
                            wonderDbDataList.add(data)
                        }

                        wonders.value = wonderDbDataList
                        loading.value = false
                        for (wonderDbData in wonderDbDataList) {
                            wonderDbRepository.insertWonder(wonderDbData)
                        }
                    }

                    override fun onError(e: Throwable) {
                        wonderLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }


    fun loadAllWondersfromDB() {
        loading.value = true
        val usersDisposable = wonderDbRepository.getAllWonders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                this.onAllWondersFetched(list)
            },
                { error ->
                    this.onError(error)
                })
        disposable!!.add(usersDisposable)
    }

    private fun onError(throwable: Throwable) {
        Log.d(TAG, throwable.message)
    }

    private fun onAllWondersFetched(wondderList: List<WonderDbData>) {
        dbWonders.value = wondderList
        loading.value = false
    }


    companion object {
        private val TAG = "UserListViewModel"
    }


}