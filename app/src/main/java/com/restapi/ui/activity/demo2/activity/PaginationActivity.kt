package com.restapi.ui.activity.demo2.activity

import ConnectionDetector
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restapi.ui.base.BaseActivity
import com.restapi.R
import com.restapi.databinding.ActivityPaginationBinding
import com.restapi.di.DiConstant
import com.restapi.ui.activity.demo2.adapter.PaginationAdapter
import com.restapi.ui.activity.demo2.pojo.SampleData
import com.restapi.ui.services.ApiService
import com.restapi.ui.viewmodel.PaginationListViewModel
import com.restapi.ui.viewmodel.UserListViewModel
import com.restapi.utils.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class PaginationActivity : BaseActivity() {

    private lateinit var binding: ActivityPaginationBinding

    private val paginationAdapter by lazy { PaginationAdapter() }

    val userList = mutableListOf<SampleData.User>()
    private var limit = 10
    private var totalUser: Int? = null
    private var offset = 0
    private var scroll = false

    private val paginationListViewModel: PaginationListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        apiCalling(offset, limit)
        observeLiveData()
        scrollListener()

    }

    private fun observeLiveData() {
        paginationListViewModel.list.observe(this) {
            scroll = false
            binding.progress.visibility = View.GONE
            hideProgressDialog()
            if (it != null) {
                totalUser = it.totalUsers
                it.users?.let { it1 -> userList.addAll(it1) }
                it.users?.let { it1 -> setUserAdapter(it1) }
            }
        }
    }

    private fun scrollListener() = with(binding) {
        recyclerViewPagination.addOnScrollListener(object :
            PaginationScrollListener(recyclerViewPagination.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                offset += 10
                scroll = true
                apiCalling(offset, limit)
            }

            override fun isLastPage() = userList.size == totalUser

            override fun isLoading() = scroll

        })
    }

    private fun setAdapter() = with(binding) {
        recyclerViewPagination.adapter = paginationAdapter
        recyclerViewPagination.addItemDecoration(ItemDecorator())
        recyclerViewPagination.layoutManager =
            LinearLayoutManager(this@PaginationActivity, LinearLayoutManager.VERTICAL, false)
    }

    private fun apiCalling(offset: Int, limit: Int) {
        if (ConnectionDetector(this).isConnectingToInternet()) {
            if (offset >= 10) {
                binding.progress.visibility = View.VISIBLE
            } else {
                showProgressDialog(this)
            }
            val data: MutableMap<String, Int?> = HashMap()
//            data["limit"] = limit
            data["offset"] = offset
            paginationListViewModel.getData(data)
//            val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.slingacademy.com/")
//                .client(Const.okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create()).build()
//
//            val apiService = retrofit.create(ApiService::class.java)


//            CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {
//                val sampleData = withContext(Dispatchers.IO) { apiService.getUserData(data) }
//                if (sampleData != null) {
//                    scroll = false
//                    binding.progress.visibility = View.GONE
//                    hideProgressDialog()
//                    if (sampleData.success == true) {
//                        val user = sampleData.users
//                        if (!user.isNullOrEmpty()) {
//                            totalUser = sampleData.totalUsers
//                            userList.addAll(user)
//                            setUserAdapter(user)
//                        }
//                    } else {
//                        binding.progress.visibility = View.GONE
//                        hideProgressDialog()
//                        showMessage(this@PaginationActivity, sampleData.message.toString())
//                    }
//                }
//            }

//            apiService.getUserData(data).enqueue(object : Callback<SampleData> {
//                override fun onResponse(call: Call<SampleData>, response: Response<SampleData>) {
//                    scroll = false
//                    if (response.isSuccessful) {
//                        binding.progress.visibility = View.GONE
//                        hideProgressDialog()
//                        if (response.body()?.success == true) {
//                            val user = response.body()?.users
//                            if (user != null) {
//                                totalUser = response.body()?.totalUsers
//                                userList.addAll(user)
//                                setUserAdapter(user)
//                            }
//                        } else {
//                            showMessage(
//                                this@PaginationActivity,
//                                response.body()?.message.toString()
//                            )
//                        }
//                    } else {
//                        binding.progress.visibility = View.GONE
//                        hideProgressDialog()
//                        val jsonObject = JSONObject(
//                            response.errorBody()!!.charStream().readText()
//                        ).getString("detail")
//                        showMessage(this@PaginationActivity, jsonObject)
//
//                    }
//                }
//
//                override fun onFailure(call: Call<SampleData>, t: Throwable) {
//                    hideProgressDialog()
//                    showMessage(this@PaginationActivity, t.message.toString())
//                }
//
//            })
        } else {
            (ConnectionDetector(this).connectionDetect())
        }
    }

    private fun setUserAdapter(user: List<SampleData.User>) {
        paginationAdapter.addItem(user)
        binding.progress.visibility = View.GONE
    }

    inner class ItemDecorator : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left = resources.getDimensionPixelSize(R.dimen._10)
            outRect.right = resources.getDimensionPixelSize(R.dimen._10)
            outRect.top = resources.getDimensionPixelSize(R.dimen._10)
        }
    }
}
