package com.restapi.ui.activity.demo2.pojo


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class SampleData {
    @SerializedName("limit")
    @Expose
    var limit: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("offset")
    @Expose
    var offset: Int? = null
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("total_users")
    @Expose
    var totalUsers: Int? = null
    @SerializedName("users")
    @Expose
    var users: List<User>? = null
    class User {
        @SerializedName("city")
        @Expose
        var city: String? = null
        @SerializedName("country")
        @Expose
        var country: String? = null
        @SerializedName("date_of_birth")
        @Expose
        var dateOfBirth: String? = null
        @SerializedName("email")
        @Expose
        var email: String? = null
        @SerializedName("first_name")
        @Expose
        var firstName: String? = null
        @SerializedName("gender")
        @Expose
        var gender: String? = null
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("job")
        @Expose
        var job: String? = null
        @SerializedName("last_name")
        @Expose
        var lastName: String? = null
        @SerializedName("latitude")
        @Expose
        var latitude: Double? = null
        @SerializedName("longitude")
        @Expose
        var longitude: Double? = null
        @SerializedName("phone")
        @Expose
        var phone: String? = null
        @SerializedName("profile_picture")
        @Expose
        var profilePicture: String? = null
        @SerializedName("state")
        @Expose
        var state: String? = null
        @SerializedName("street")
        @Expose
        var street: String? = null
        @SerializedName("zipcode")
        @Expose
        var zipcode: String? = null
    }
}