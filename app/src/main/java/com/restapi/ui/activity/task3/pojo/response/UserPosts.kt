package com.restapi.ui.activity.task3.pojo.response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class UserPosts : ArrayList<UserPosts.UserPostsItem>(){
    class UserPostsItem {
        @SerializedName("body")
        @Expose
        var body: String? = null
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("userId")
        @Expose
        var userId: Int? = null
    }
}