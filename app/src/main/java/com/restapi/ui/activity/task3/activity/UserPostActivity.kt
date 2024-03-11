package com.restapi.ui.activity.task3.activity

import ConnectionDetector
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restapi.ui.base.BaseActivity
import com.restapi.Const
import com.restapi.R
import com.restapi.databinding.ActivityUserPostBinding
import com.restapi.ui.activity.task3.adapter.FilterPostAdapter
import com.restapi.ui.activity.task3.adapter.UserPostAdapter
import com.restapi.ui.activity.task3.interfaces.ClickListener
import com.restapi.ui.activity.task3.pojo.response.UserPost
import com.restapi.ui.activity.task3.pojo.response.UserPosts
import com.restapi.ui.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserPostActivity : BaseActivity() {

    private lateinit var binding: ActivityUserPostBinding

    private val userPostAdapter by lazy { UserPostAdapter(PostClickListener()) }


    private val filterPostAdapter by lazy { FilterPostAdapter() }
    private var retrofit: Retrofit? = null
    val userList = mutableListOf<UserPost>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        apiCall()
    }

    private fun setAdapter() = with(binding) {
        recyclerViewUsrPost.adapter = userPostAdapter
        recyclerViewUsrPost.addItemDecoration(ItemDecorator())
        recyclerViewUsrPost.layoutManager =
            LinearLayoutManager(this@UserPostActivity, LinearLayoutManager.VERTICAL, false)
    }

    private fun create(): ApiService {

        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .client(Const.okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!.create(ApiService::class.java)
    }

    private fun apiCall() {

        if (ConnectionDetector(this).isConnectingToInternet()) {
            Const.showProgressDialog(this)
            val apiService = create()

            val userPosts = object : Callback<UserPosts> {
                override fun onResponse(call: Call<UserPosts>, response: Response<UserPosts>) {
                    if (response.isSuccessful) {
                        Const.hideProgressDialog()
                        val data = response.body()
                        if (data != null) {
                            for (i in data.indices) {
                                if (!userList.contains(UserPost(data[i].userId))) {
                                    userList.add(UserPost(data[i].userId))
                                }
                            }
                            Log.e("TAG", "onResponse: $userList")
                            setUserPostList(userList)
                        }
                    }
                }

                override fun onFailure(call: Call<UserPosts>, t: Throwable) {
                    Const.hideProgressDialog()
                    Const.showMessage(this@UserPostActivity, t.message.toString())
                }

            }

            apiService.getUserPost().enqueue(userPosts)
        }
    }

    private fun setUserPostList(userList: MutableList<UserPost>) {
        userPostAdapter.addItem(userList)
    }


    inner class ItemDecorator : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left = resources.getDimensionPixelSize(R.dimen._10)
            outRect.right = resources.getDimensionPixelSize(R.dimen._10)
            outRect.top = resources.getDimensionPixelSize(R.dimen._10)
        }
    }

    inner class PostClickListener : ClickListener {
        override fun onClick(id: Int?, position: Int, v: View?) {
            filterPostApiCall(id, position)
        }

        private fun filterPostApiCall(id: Int?, position: Int) {

            if (ConnectionDetector(this@UserPostActivity).isConnectingToInternet()) {
                Const.showProgressDialog(this@UserPostActivity)


                val apiService = create()

                val userPosts = object : Callback<UserPosts> {
                    override fun onResponse(call: Call<UserPosts>, response: Response<UserPosts>) {
                        if (response.isSuccessful) {
                            Const.hideProgressDialog()
                            val data = response.body()
                            if (data != null) {
                                userList[position].userPosts?.addAll(data)
                                userPostAdapter.notifyItemChanged(position)
//                            userPostAdapter.filterPostAdapter.addItem(data)
                            }
                        }
                    }

                    override fun onFailure(call: Call<UserPosts>, t: Throwable) {
                        Const.hideProgressDialog()
                        Const.showMessage(this@UserPostActivity, t.message.toString())
                    }
                }

                apiService.getUserPost(id).enqueue(userPosts)
            }
        }
    }
}