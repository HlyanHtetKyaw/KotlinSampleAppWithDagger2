package com.example.testforcodigo.ui.list

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.example.testforcodigo.data.database.WonderDbRepository
import com.example.testforcodigo.data.model.Wonder
import com.example.testforcodigo.data.model.WonderDbData
import com.example.testforcodigo.data.model.WonderResponse
import com.example.testforcodigo.data.rest.ApiRepository
import com.example.testforcodigo.util.MainUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WonderListViewModel @Inject
constructor(
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

    fun loadWondersFromApi() {
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

                        for (i in response.wonders!!.indices) {
                            val data = WonderDbData()
                            data.id = i.toLong()
                            data.description = response.wonders!![i].description
                            data.image = response.wonders!![i].image
                            data.location = response.wonders!![i].location
                            data.lat = response.wonders!![i].lat
                            data.long = response.wonders!![i].long
                            wonderDbRepository.insertWonder(data)
                        }
                    }

                    override fun onError(e: Throwable) {
                        onErrorLoading()
                    }
                })
        )
    }


    fun loadWondersfromDB(context: Context?) {
        loading.value = true
        val usersDisposable = wonderDbRepository.getAllWonders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.d(TAG, Gson().toJson(list))
                if (list.isEmpty()) {
                    if (MainUtils.isNetworkAvailable(context!!)) {
                        loadWondersFromApi()
                    } else {
                        onErrorLoading()
                    }
                } else {
                    this.onAllWondersFetched(list)
                }
            },
                {
                    onErrorLoading()
                })
        disposable!!.add(usersDisposable)
    }

    private fun onErrorLoading() {
        error.value = true
        loading.value = false
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