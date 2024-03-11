package com.restapi.ui.activity.task1.pojo.response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Fact {
    @SerializedName("fact")
    @Expose
    var fact: String? = null
    @SerializedName("length")
    @Expose
    var length: Int? = null
}