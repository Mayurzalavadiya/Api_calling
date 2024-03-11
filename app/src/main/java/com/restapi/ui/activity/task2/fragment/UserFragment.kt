package com.restapi.ui.activity.task2.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.restapi.Const
import com.restapi.R
import com.restapi.ui.base.BaseFragment
import com.restapi.databinding.FragmentUserBinding
import com.restapi.ui.activity.task2.pojo.response.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFragment : BaseFragment() {

    private lateinit var binding: FragmentUserBinding
    var id: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            id = requireArguments().getInt(Const.ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiCall()
    }

    private fun apiCall() = with(binding)
    {
        Const.showProgressDialog(requireActivity())
        val apiService = Const.create()

        fun getHeaderMap(): Map<String, String> {
            val headerMap = mutableMapOf<String, String>()
            headerMap["Device-Type"] = "Android"
            headerMap["Accept-Path"] = "A"
            headerMap["Cache-Control"] = Const.currentDateTime("yyyy/MMM/dd GGG hh:mm a")
            return headerMap
        }

            CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {
            val user = withContext(Dispatchers.IO) { apiService.getUser(getHeaderMap(), id) }

            if (user != null) {
                Const.hideProgressDialog()
                val data = user.data
                if (data != null) {
                    constraintLayout.setBackgroundColor(Color.parseColor(data.color))
                    textViewUserName.text = data.name
                    textViewUserId.text = data.id.toString()
                    textViewUserYear.text = data.year.toString()
                    textViewUserPantoneValue.text = data.pantoneValue
                }

            } else {
                Const.hideProgressDialog()
                showMessage(requireActivity(), getString(R.string.data_not_available))
            }
        }
        val user = object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful) {
                    Const.hideProgressDialog()

                    if (response.body() != null) {
                        constraintLayout.setBackgroundColor(Color.parseColor(response.body()?.data?.color))
                        textViewUserName.text = response.body()?.data?.name
                        textViewUserId.text = response.body()?.data?.id.toString()
                        textViewUserYear.text = response.body()?.data?.year.toString()
                        textViewUserPantoneValue.text = response.body()?.data?.pantoneValue

                    }

                } else {
                    Const.hideProgressDialog()
                    val jsonObject = JSONObject(
                        response.errorBody()!!.charStream().readText()
                    ).getString("error")
                    Const.showMessage(requireActivity(), jsonObject)
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Const.hideProgressDialog()
                Const.showMessage(requireActivity(), t.message.toString())
            }

        }

//        apiService.getUser(getHeaderMap(), id).enqueue(user)
    }

}