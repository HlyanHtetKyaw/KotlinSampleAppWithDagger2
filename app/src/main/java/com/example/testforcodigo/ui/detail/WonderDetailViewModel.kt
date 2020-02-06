package com.example.testforcodigo.ui.detail

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.testforcodigo.data.model.Wonder
import com.example.testforcodigo.util.Constants
import com.example.testforcodigo.util.Constants.WONDER_DETAIL
import com.google.gson.Gson
import javax.inject.Inject


class WonderDetailViewModel @Inject
constructor(
) : ViewModel() {

    private val wonderDetail = MutableLiveData<Wonder>()


    internal fun getWonderDetail(): MutableLiveData<Wonder> {
        return wonderDetail
    }

    fun loadWonderDetail(activity: Activity) {
        val mPrefs = activity.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
        val gSon = Gson()
        val json: String? = mPrefs.getString(WONDER_DETAIL, "")
        val wonder: Wonder = gSon.fromJson(json, Wonder::class.java)
        wonderDetail.value = wonder
    }

}