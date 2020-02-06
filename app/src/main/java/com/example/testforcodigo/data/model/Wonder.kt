package com.example.testforcodigo.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Wonder {
    @SerializedName("location")
    @Expose
    var location: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("lat")
    @Expose
    var lat: String? = null
    @SerializedName("long")
    @Expose
    var long: String? = null

}
