package com.restapi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restapi.Const
import com.restapi.di.DiConstant
import com.restapi.ui.activity.demo2.pojo.SampleData
import com.restapi.ui.services.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PaginationListViewModel @Inject constructor(@Named(DiConstant.BASE_URL_PAGINATION) val apiService: ApiService) :
    ViewModel() {

    val list = MutableLiveData<SampleData>()

    fun getData(data: MutableMap<String, Int?>) {
        viewModelScope.launch {
            val pageList = apiService.getUserData(data)
            list.value = pageList
        }
    }
}