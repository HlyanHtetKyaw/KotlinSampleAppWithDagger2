package com.example.testforcodigo.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class WonderResponse {
    @SerializedName("wonders")
    @Expose
    var wonders: List<Wonder>? = null

}
