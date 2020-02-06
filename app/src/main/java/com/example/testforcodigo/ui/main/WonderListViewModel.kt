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
import javax.inject.Inject


class WonderListViewModel @Inject
constructor(
    application: Application,
    private val apiRepository: ApiRepository,
    private val wonderDbRepository: WonderDbRepository
) : ViewModel() {
    private var disposable: CompositeDisposable? = null

    private val wonders = MutableLiveData<List<Wonder>>()
    private val error = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()

    init {
        disposable = CompositeDisposable()
    }

    internal fun getWonders(): LiveData<List<Wonder>> {
        return wonders
    }

    internal fun getLoading(): LiveData<Boolean> {
        return loading
    }

    internal fun getError(): LiveData<Boolean> {
        return error
    }

    fun fetchWondersFromApi() {
        loading.value = true
        disposable!!.add(
            apiRepository.getWonders().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                    DisposableSingleObserver<WonderResponse>() {
                    override fun onSuccess(response: WonderResponse) {
                        Log.d(TAG, "onSuccess: Response" + Gson().toJson(response))
                        error.value = false
                        wonders.value = response.wonders
                        loading.value = false

                        for (wonder: Wonder in response.wonders!!) {
                            val data = WonderDbData()
                            data.description = wonder.description
                            data.image = wonder.image
                            data.location = wonder.location
                            data.lat = wonder.lat
                            data.long = wonder.long
                            wonderDbRepository.insertWonder(data)
                        }
                    }

                    override fun onError(e: Throwable) {
                        onErrorLoading(e)
                    }
                })
        )
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
                    onErrorLoading(error)
                })
        disposable!!.add(usersDisposable)
    }

    private fun onErrorLoading(throwable: Throwable) {
        error.value = true
        loading.value = false
        Log.d(TAG, throwable.message)
    }

    private fun onAllWondersFetched(wonderList: List<WonderDbData>) {
        val wonderResponse = ArrayList<Wonder>()

        for (wonderDbData: WonderDbData in wonderList) {
            val wonder = Wonder()
            wonder.description = wonderDbData.description
            wonder.image = wonderDbData.image
            wonder.lat = wonderDbData.lat
            wonder.location = wonderDbData.location
            wonder.long = wonderDbData.long
            wonderResponse.add(wonder)
        }

        wonders.value = wonderResponse
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }

    companion object {
        private const val TAG = "UserListViewModel"
    }


}