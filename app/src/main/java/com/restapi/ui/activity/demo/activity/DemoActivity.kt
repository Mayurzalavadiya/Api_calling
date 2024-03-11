package com.restapi.ui.activity.demo.activity

import ConnectionDetector
import android.graphics.Rect
import androidx.activity.viewModels
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restapi.ui.base.BaseActivity
import com.restapi.Const
import com.restapi.R
import com.restapi.databinding.ActivityDemoBinding
import com.restapi.ui.activity.demo.adapter.UserAdapter
import com.restapi.ui.activity.demo.pojo.response.User
import com.restapi.ui.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DemoActivity : BaseActivity() {

    private lateinit var binding: ActivityDemoBinding

    private val userAdapter by lazy {
        UserAdapter()
    }

    private val viewModel: UserListViewModel by viewModels()

    private val usersList = mutableListOf<User.Datum>()

    private var pageNum = 1

    private var totalPage: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Initialize Dagger injection
        //  (application as App).appComponent.inject(this)

        setAdapter()
        apiCall(pageNum)
        observeLiveData()
        scrollListener()
    }


    private fun scrollListener() = with(binding) {

        recyclerViewUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    val lManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = lManager.childCount
                    val pastVisibleItem = lManager.findFirstVisibleItemPosition()
                    val total = userAdapter.itemCount

                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        if (totalPage!! > pageNum) {
                            pageNum++
                            apiCall(pageNum)
                        }
                    }

                    super.onScrolled(recyclerView, dx, dy)
                }
            }
        })
    }

    private fun setAdapter() = with(binding.recyclerViewUsers) {
        adapter = userAdapter
        addItemDecoration(ItemDecorator())
        layoutManager = LinearLayoutManager(this@DemoActivity, LinearLayoutManager.VERTICAL, false)
    }


    private fun observeLiveData() {
        viewModel.userList.observe(this) {
            if (it != null) {
                totalPage = it.totalPages
                it.data?.let { it1 -> usersList.addAll(it1) }

                if (usersList.size < 10 && totalPage!! > pageNum) {
                    pageNum++
                    apiCall(pageNum)
                } else {
                    hideProgressDialog()
                    setUserAdapter(usersList)
                }
            }
        }
    }

    private fun apiCall(page: Int) {
        if (page > 1 && usersList.size > 10) {
            binding.progress.visibility = View.VISIBLE
        } else {
            showProgressDialog(this@DemoActivity)
        }
        viewModel.getCountryList(page)


//                apiService?.getMultiUsers(page)?.enqueue(object : Callback<User> {
//
//                    @SuppressLint("NotifyDataSetChanged")
//                    override fun onResponse(call: Call<User>, response: Response<User>) {
//                        if (response.isSuccessful) {
//                            binding.progress.visibility = View.GONE
//                            Const.hideProgressDialog()
//                            val data = response.body()?.data
//                            if (data != null) {
//                                totalPage = response.body()?.totalPages
//                                usersList.addAll(data)
//                                if (usersList.size < 10) {
//                                    pageNum++
//                                    apiCall(pageNum)
//                                }
//                                if (usersList.size > 10) {
//                                    setUserAdapter(usersList)
//                                }
//                            }
//                        } else {
//                            Const.hideProgressDialog()
//                            val jsonObject = JSONObject(
//                                response.errorBody()!!.charStream().readText()
//                            ).getString("error")
//                            Const.showMessage(this@DemoActivity, jsonObject)
//
//                        }
//                    }
//
//                    override fun onFailure(call: Call<User>, t: Throwable) {
//                        Const.hideProgressDialog()
//                        binding.progress.visibility = View.GONE
//                        Const.showMessage(
//                            this@DemoActivity,
//                            t.message.toString()
//                        )
//                        Log.e("TAG", "onFailure: ${t.message}")
//                    }
//                })


    }

    private fun setUserAdapter(usersList: List<User.Datum>) {
        userAdapter.addItem(usersList)
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


//    private fun isNetworkConnected(): Boolean {
//        val cm: ConnectivityManager?
//        cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
//        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
//    }


}