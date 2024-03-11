package com.restapi.ui.activity.task2.pojo.response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class UsersList {
    @SerializedName("data")
    @Expose
    var data: List<Data>? = null
    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null
    @SerializedName("support")
    @Expose
    var support: Support? = null
    @SerializedName("total")
    @Expose
    var total: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    class Data {
        @SerializedName("color")
        @Expose
        var color: String? = null
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("pantone_value")
        @Expose
        var pantoneValue: String? = null
        @SerializedName("year")
        @Expose
        var year: Int? = null
    }

    class Support {
        @SerializedName("text")
        @Expose
        var text: String? = null
        @SerializedName("url")
        @Expose
        var url: String? = null
    }
}