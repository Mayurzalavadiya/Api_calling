package com.restapi.ui.activity.demo.pojo.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class User {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    @SerializedName("support")
    @Expose
    var support: Support? = null

    class Datum {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("first_name")
        @Expose
        var firstName: String? = null

        @SerializedName("last_name")
        @Expose
        var lastName: String? = null

        @SerializedName("avatar")
        @Expose
        var avatar: String? = null
    }

    class Support {
        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("text")
        @Expose
        var text: String? = null
    }

}