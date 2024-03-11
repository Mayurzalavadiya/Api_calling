package com.restapi.ui.activity.demo3.activity

import ConnectionDetector
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.restapi.ui.base.BaseActivity
import com.google.gson.Gson
import com.restapi.Const
import com.restapi.R
import com.restapi.databinding.ActivityPostBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostActivity : BaseActivity() {

    private lateinit var binding: ActivityPostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() = with(binding) {
        buttonPost.setOnClickListener {
            if (checkValidation()) {
                postApiCalling()
            }
        }
        buttonPut.setOnClickListener {
            if (readStringPrefVal(ID)!=null){
            if (checkValidation()) {
                putApiCalling()
            }
            }
        }
        buttonDelete.setOnClickListener {
            if (readStringPrefVal(ID)!=null){
                deleteApiCalling()
            }
        }
    }

    private fun checkValidation(): Boolean = with(binding) {
        val name = editTextName.text.toString().trim()
        val job = editTextJob.text.toString().trim()

        if (name.isEmpty()) {
            Const.showMessage(
                this@PostActivity,
                this@PostActivity.getString(R.string.validation_please_enter_a_name)
            )
            editTextName.requestFocus()
            return false
        } else if (job.isEmpty()) {
            Const.showMessage(
                this@PostActivity,
                this@PostActivity.getString(R.string.validation_please_enter_a_job)
            )
            editTextJob.requestFocus()
            return false
        } else {
            return true
        }
    }

    private fun postApiCalling() = with(binding) {
        if (ConnectionDetector(this@PostActivity).isConnectingToInternet()) {
            showProgressDialog(this@PostActivity)
            val apiService = Const.create()


            CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {
                val userList = withContext(Dispatchers.IO) {
                    apiService.setUser(
                        editTextName.text.toString().trim(),
                        editTextJob.text.toString().trim()
                    )
                }

                if (userList != null) {
                    hideProgressDialog()
                    savePrefsVal(ID, userList.id!!)
                    clearData()
                    showMessage(userList.name)
                } else {
                    hideProgressDialog()
                    showMessage(this@PostActivity, getString(R.string.data_not_available))
                }

            }
//            apiService.setUser(
//                editTextName.text.toString().trim(),
//                editTextJob.text.toString().trim()
//            )
//                .enqueue(object : Callback<UserCreate> {
//                    override fun onResponse(
//                        call: Call<UserCreate>,
//                        response: Response<UserCreate>
//                    ) {
//                        if (response.isSuccessful) {
//                            hideProgressDialog()
//                            id = response.body()?.id
//                            clearData()
//                            showMessage(response.body()?.name)
//                            Log.e("TAG", "onResponse: ${Gson().toJson(response.body())}")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<UserCreate>, t: Throwable) {
//                        hideProgressDialog()
//                        showMessage(t.message.toString())
//                    }
//
//                })
        } else {
            ConnectionDetector(this@PostActivity).connectionDetect()
            hideProgressDialog()
        }
    }


    private fun putApiCalling() = with(binding) {
        if (ConnectionDetector(this@PostActivity).isConnectingToInternet()) {
            showProgressDialog(this@PostActivity)
            val apiService = Const.create()
            // Create JSON using JSONObject

            val params = HashMap<String?, String?>()
            params["name"] = editTextName.text.toString().trim()
            params["job"] = editTextJob.text.toString().trim()


            CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {
                val userList = withContext(Dispatchers.IO) {
                    apiService.updateUser(
                        readStringPrefVal(ID),
                        editTextName.text.toString().trim(),
                        editTextJob.text.toString().trim()
                    )
                }

                if (userList != null) {
                    hideProgressDialog()
                    clearData()
                    showMessage(userList.name)
                    Log.e("TAG", "onResponse: ${Gson().toJson(userList)}")
                } else {
                    hideProgressDialog()
                    showMessage(this@PostActivity, getString(R.string.data_not_available))
                }

            }

//            apiService.updateUser(
//                id, editTextName.text.toString().trim(),
//                editTextJob.text.toString().trim()
//            )
//                .enqueue(object : Callback<PutUser> {
//                    override fun onResponse(
//                        call: Call<PutUser>,
//                        response: Response<PutUser>
//                    ) {
//                        if (response.isSuccessful) {
//                            hideProgressDialog()
//                            showMessage(response.body()?.name)
//                            clearData()
//                            Log.e("TAG", "onResponse: ${Gson().toJson(response.body())}")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<PutUser>, t: Throwable) {
//                        hideProgressDialog()
//                        showMessage(t.message.toString())
//                    }
//
//                })
        } else {
            ConnectionDetector(this@PostActivity).connectionDetect()
            hideProgressDialog()
        }
    }

    private fun deleteApiCalling() {
        if (ConnectionDetector(this@PostActivity).isConnectingToInternet()) {
            showProgressDialog(this@PostActivity)
            val apiService = Const.create()

            CoroutineScope(Dispatchers.Main).launch(scopeExceptionHandling) {

                val userList = withContext(Dispatchers.IO) { apiService.deleteUser(readStringPrefVal(ID)) }

                if (userList != null) {
                    hideProgressDialog()
                    Log.e("TAG", "onResponse: ${Gson().toJson(userList)}")
                    showMessage(getString(R.string.validation_delete_successfully))
                } else {
                    hideProgressDialog()
                    showMessage(this@PostActivity, getString(R.string.data_not_available))
                }

            }
//            apiService.deleteUser(id)
//                .enqueue(object : Callback<Any> {
//                    override fun onResponse(
//                        call: Call<Any>,
//                        response: Response<Any>
//                    ) {
//                        if (response.isSuccessful) {
//                            hideProgressDialog()
//                            showMessage(getString(R.string.validation_delete_successfully))
//                            clearData()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Any>, t: Throwable) {
//                        hideProgressDialog()
//                        showMessage(t.message.toString())
//                    }
//
//                })
        } else {
            ConnectionDetector(this@PostActivity).connectionDetect()
            hideProgressDialog()
        }
    }


    private fun clearData() = with(binding) {
        editTextName.text = null
        editTextJob.text = null
    }

    fun showMessage(msg: String?) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}