package com.restapi.ui.services

import com.restapi.ui.activity.task1.pojo.response.Facts
import com.restapi.ui.activity.demo.pojo.response.User
import com.restapi.ui.activity.demo2.pojo.SampleData
import com.restapi.ui.activity.demo3.pojo.PutUser
import com.restapi.ui.activity.demo3.pojo.UserCreate
import com.restapi.ui.activity.task1.pojo.response.Fact
import com.restapi.ui.activity.task2.pojo.response.UsersList
import com.restapi.ui.activity.task3.pojo.response.UserPosts
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    @Headers(
        "Device-Type: Android",
        "Accept-Pat: true"
    )
    @GET("api/users")
    suspend fun getMultiUsers(@Query("page") page: Int?): User

    @GET("facts")
    suspend fun getFacts(@Header("Device-Type") types: String): Facts

    @GET("fact")
    suspend fun getFact(): Fact

    @GET("v1/sample-data/users")
suspend fun     getUserData(@QueryMap queryMap: Map<String, Int?>): SampleData

    @FormUrlEncoded  //key value pair
    @POST("api/users")
    suspend fun setUser(
        @Field("name") name: String?,
        @Field("job") job: String?
    ): UserCreate?

    @FormUrlEncoded
    @PUT("api/users/{id}")
   suspend fun updateUser(
        @Path("id") id: String?,
        @Field("name") name: String?,
        @Field("job") job: String?
//        @FieldMap params: HashMap<String?, String?>
    ): PutUser?

    @DELETE("api/users/{id}")
    suspend fun deleteUser(@Path("id") id: String?): Any?

    //List of Users

    @GET("api/unknown")
    suspend fun getListOfUsers(@Query("page") page: Int?): UsersList?

    //User
    @GET("api/unknown/{id}")
    suspend fun getUser(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: Int?
    ): com.restapi.ui.activity.task2.pojo.response.User?

    //UserPost
    @GET("posts")
    fun getUserPost(): Call<UserPosts>

    //UserPost
    @GET("posts")
    fun getUserPost(@Query("userId") userId: Int?): Call<UserPosts>

}