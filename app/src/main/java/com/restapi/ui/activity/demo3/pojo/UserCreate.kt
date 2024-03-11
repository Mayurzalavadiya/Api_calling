package com.restapi.ui.activity.demo3.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class UserCreate {
    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("job")
    @Expose
    var job: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
}