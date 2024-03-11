package com.restapi.ui.activity.task3.pojo.response

data class UserPost(
    val userId: Int?,
    val userPosts: MutableList<UserPosts.UserPostsItem>? = null
)
