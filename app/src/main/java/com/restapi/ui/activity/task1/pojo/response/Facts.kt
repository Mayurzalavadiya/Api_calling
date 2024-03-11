package com.restapi.ui.activity.task1.pojo.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Facts {
    @SerializedName("current_page")
    @Expose
    var currentPage: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String? = null

    @SerializedName("from")
    @Expose
    var from: Int? = null

    @SerializedName("last_page")
    @Expose
    var lastPage: Int? = null

    @SerializedName("last_page_url")
    @Expose
    var lastPageUrl: String? = null

    @SerializedName("links")
    @Expose
    var links: List<Link>? = null

    @SerializedName("next_page_url")
    @Expose
    var nextPageUrl: String? = null

    @SerializedName("path")
    @Expose
    var path: String? = null

    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null

    @SerializedName("prev_page_url")
    @Expose
    var prevPageUrl: Any? = null

    @SerializedName("to")
    @Expose
    var to: Int? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null

    class Datum {
        @SerializedName("fact")
        @Expose
        var fact: String? = null

        @SerializedName("length")
        @Expose
        var length: Int? = null
    }
    class Link {
        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("label")
        @Expose
        var label: String? = null

        @SerializedName("active")
        @Expose
        var active: Boolean? = null
    }

}