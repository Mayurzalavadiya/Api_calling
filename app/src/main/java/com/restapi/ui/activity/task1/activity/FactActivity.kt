package com.restapi.ui.activity.task1.activity

import ConnectionDetector
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restapi.ui.base.BaseActivity
import com.restapi.R
import com.restapi.databinding.ActivityFactBinding
import com.restapi.ui.activity.task1.adapter.FactAdapter
import com.restapi.ui.activity.task1.interfaces.ClickListener
import com.restapi.ui.activity.task1.pojo.FactData
import com.restapi.ui.viewmodel.FactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FactActivity : BaseActivity(), ClickListener {

    private lateinit var binding: ActivityFactBinding

    private val factAdapter by lazy { FactAdapter(this) }

    private val factList = mutableListOf<FactData>()

    private val factViewModel: FactViewModel by viewModels()

    private var factPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFactBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        (application as App).appComponent.inject(this)
        observeLiveData()
        setAdapter()
        apiCalling()
    }

    private fun observeLiveData() {
        factViewModel.factsList.observe(this) {
            hideProgressDialog()
            if (it != null) {

                val data = it.data
                if (!data.isNullOrEmpty()) {

                    for (i in data.indices) {
                        factList.add(FactData(data[i].fact, data[i].length))
                    }
                    setFactList(factList)
                }
            }
        }

        factViewModel.factList.observe(this) {
            hideProgressDialog()
            hideProgressDialog()
            if (it != null) {

                val data = it.fact
                factPosition?.let { position ->
                    factList[position].text = data

                    factAdapter.notifyItemChanged(position)
                }
            }
        }
    }

    private fun setAdapter() = with(binding) {
        recyclerViewFact.adapter = factAdapter
        recyclerViewFact.addItemDecoration(ItemDecorator())
        recyclerViewFact.layoutManager =
            LinearLayoutManager(this@FactActivity, LinearLayoutManager.VERTICAL, false)
    }


    private fun apiCalling() {

        val isInternetConnected = ConnectionDetector(this).isConnectingToInternet()
        if (isInternetConnected) {
            showProgressDialog(this)

//            val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://catfact.ninja/")
//                .client(Const.okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create()).build()
//
//            val apiService = retrofit.create(ApiService::class.java)

            factViewModel.getFactsList("Android")

//            CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {
//                val facts = withContext(Dispatchers.IO) { apiService.getFacts("Android") }
//                Log.e("TAG", "apiCalling: ${Gson().toJson(facts)}")
//                if (facts != null) {
//                    hideProgressDialog()
//                    val data = facts.data
//                    if (!data.isNullOrEmpty()) {
//
//                        for (i in data.indices) {
//                            factList.add(FactData(data[i].fact, data[i].length))
//                        }
//                        withContext(Dispatchers.Main) {
//                            setFactList(factList)
//                        }
//                    }
//
//                } else {
//                    hideProgressDialog()
//                    showMessage(this@FactActivity, getString(R.string.data_not_available))
//                }
//            }
//            apiService.getFacts("Android").enqueue(object : Callback<Facts> {
//                override fun onResponse(call: Call<Facts>, response: Response<Facts>) {
//                    if (response.isSuccessful) {
//                        Const.hideProgressDialog()
//                        val data = response.body()?.data
//                        if (!data.isNullOrEmpty()) {
//
//                            for (i in data.indices) {
//                                factList.add(FactData(data[i].fact, data[i].length))
//                            }
//
//                            setFactList(factList)
//                        }
//
//                    }
//                }
//
//                override fun onFailure(call: Call<Facts>, t: Throwable) {
//                    Const.hideProgressDialog()
//                    Toast.makeText(this@FactActivity, t.message, Toast.LENGTH_SHORT).show()
//                }
//
//            })
        }
    }

    private fun setFactList(factList: MutableList<FactData>) {
        factAdapter.addItem(factList)
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

    override fun onClick(position: Int, v: View?) {
        apiCall(position)
    }

    private fun apiCall(position: Int) {
        val isInternetConnected = ConnectionDetector(this).isConnectingToInternet()
        if (isInternetConnected) {
            factPosition = position
            showProgressDialog(this)

            factViewModel.getFactList()

//            CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {
//                val fact = withContext(Dispatchers.IO) { apiService.getFact() }
//                if (fact != null) {
//                    hideProgressDialog()
//                    val data = fact.fact
//                    factList[position].text = data
//
//                    withContext(Dispatchers.Main) {
//                        factAdapter.notifyItemChanged(position)
//                    }
//                } else {
//                    hideProgressDialog()
//                    showMessage(this@FactActivity, getString(R.string.data_not_available))
//                }
//            }


//            apiService.getFact().enqueue(object : Callback<Fact> {
//                override fun onResponse(call: Call<Fact>, response: Response<Fact>) {
//                    if (response.isSuccessful) {
//                        Const.hideProgressDialog()
//                        val data = response.body()?.fact
//                        Log.e("TAG", "onResponse: $data")
//                        factList[position].text = data
//                        factAdapter.notifyItemChanged(position)
//                    }
//                }
//                override fun onFailure(call: Call<Fact>, t: Throwable) {
//                    Const.hideProgressDialog()
//                    Toast.makeText(this@FactActivity, t.message, Toast.LENGTH_SHORT).show()
//                }
//            })
        }
    }

}
