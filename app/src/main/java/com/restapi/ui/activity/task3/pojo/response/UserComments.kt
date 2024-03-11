package com.restapi.ui.activity.task3.pojo.response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class UserComments : ArrayList<UserComments.UserCommentsItem>(){
    class UserCommentsItem {
        @SerializedName("body")
        @Expose
        var body: String? = null
        @SerializedName("email")
        @Expose
        var email: String? = null
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("postId")
        @Expose
        var postId: Int? = null
    }
}