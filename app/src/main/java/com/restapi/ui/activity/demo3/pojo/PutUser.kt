package com.restapi.ui.activity.demo3.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class PutUser {
    @SerializedName("job")
    @Expose
    var job: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("updatedAt")
    @Expose
    var updatedAt: String? = null
}