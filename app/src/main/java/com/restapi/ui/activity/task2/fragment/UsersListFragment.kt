package com.restapi.ui.activity.task2.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restapi.Const
import com.restapi.R
import com.restapi.ui.base.BaseFragment
import com.restapi.databinding.FragmentUsersListBinding
import com.restapi.ui.activity.task2.adapter.UsersListAdapter
import com.restapi.ui.activity.task2.interfaces.ClickListenerPosition
import com.restapi.ui.activity.task2.pojo.response.UsersList
import com.restapi.utils.PaginationScrollListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersListFragment : BaseFragment(), ClickListenerPosition {

    private lateinit var binding: FragmentUsersListBinding

    private val usersListAdapter by lazy {
        UsersListAdapter(this)
    }

    val userList = mutableListOf<UsersList.Data>()
    private var evenYearList = listOf<UsersList.Data?>()

    private var pageNum = 1
    private var totalPage: Int? = 1
    private var isLoading = false
    private var totalUser: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        pageNum = 1
        userList.clear()
        usersListAdapter.clearData()
        usersListApiCall(pageNum)
        scrollListener()
    }

    private fun setAdapter() = with(binding) {

        recyclerViewUsersList.adapter = usersListAdapter
        recyclerViewUsersList.addItemDecoration(ItemDecorator())
        recyclerViewUsersList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun scrollListener() = with(binding) {
        recyclerViewUsersList.addOnScrollListener(object :
            PaginationScrollListener(recyclerViewUsersList.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                pageNum++
                usersListApiCall(pageNum)
            }

            override fun isLastPage() = totalPage == pageNum

            override fun isLoading() = isLoading
        })
    }

    private fun usersListApiCall(page: Int) = with(binding) {

        if (page > 1 && evenYearList.size > 6) {
            binding.progress.visibility = View.VISIBLE
        } else {
            showProgressDialog(requireActivity())
        }

        val apiService = Const.create()

        CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {
            val allUsers = withContext(Dispatchers.IO) { apiService.getListOfUsers(page) }

            if (allUsers != null) {
                hideProgressDialog()
                binding.progress.visibility = View.GONE
                val data = allUsers.data
                if (data != null) {
                    totalUser = allUsers.total
                    totalPage = allUsers.totalPages
                    userList.addAll(data)
                    setUserAdapter(userList)

                }
            } else {
                hideProgressDialog()
                binding.progress.visibility = View.GONE
                showMessage(requireActivity(), getString(R.string.data_not_available))
            }
        }

        val usersList = object : Callback<UsersList> {
            override fun onResponse(call: Call<UsersList>, response: Response<UsersList>) {

                if (response.isSuccessful) {
                    hideProgressDialog()
                    binding.progress.visibility = View.GONE
                    val data = response.body()?.data
                    if (data != null) {
                        totalUser = response.body()?.total
                        totalPage = response.body()?.totalPages
                        userList.addAll(data)
                        setUserAdapter(userList)

                    }
                } else {
                    hideProgressDialog()
                    binding.progress.visibility = View.GONE
                    val jsonObject = JSONObject(
                        response.errorBody()!!.charStream().readText()
                    ).getString("error")
                    showMessage(requireActivity(), jsonObject)
                }

            }

            override fun onFailure(call: Call<UsersList>, t: Throwable) {
                hideProgressDialog()
                showMessage(requireActivity(), t.message.toString())
            }

        }

//        apiService.getListOfUsers(page).enqueue(usersList)
    }


    private fun setUserAdapter(usersList: List<UsersList.Data?>) {

        evenYearList = usersList.filter { it?.year!! % 2 == 0 }
        if (evenYearList.size < 6) {
            pageNum++
            usersListApiCall(pageNum)
        }
        if (evenYearList.size > 5) {
            usersListAdapter.addUser(evenYearList)
        }
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

    override fun onClick(position: Int?, v: View?) {
        val userFragment = UserFragment()
        userFragment.arguments = Bundle().apply {
            if (position != null) {
                putInt(Const.ID, position)
            }
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, userFragment)
            .addToBackStack(UserFragment::class.java.simpleName)
            .commit()
    }
}