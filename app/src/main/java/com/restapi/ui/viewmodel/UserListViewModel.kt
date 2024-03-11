package com.restapi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restapi.Const
import com.restapi.di.DiConstant
import com.restapi.ui.activity.demo.pojo.response.User
import com.restapi.ui.services.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserListViewModel @Inject constructor(@Named(DiConstant.BASE_URL) private val apiService: ApiService) :
    ViewModel() {

    var userList = MutableLiveData<User>()

    fun getCountryList(page: Int) {
        viewModelScope.launch(Const.scopeExceptionHandling) {
            val users = apiService.getMultiUsers(page)
            Const.hideProgressDialog()
            userList.value = users
        }


    }
}