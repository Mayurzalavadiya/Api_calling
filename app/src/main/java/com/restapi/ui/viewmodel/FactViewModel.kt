package com.restapi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restapi.ui.activity.task1.pojo.response.Facts
import com.restapi.di.DiConstant
import com.restapi.ui.activity.task1.pojo.response.Fact
import com.restapi.ui.services.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FactViewModel @Inject constructor(@Named(DiConstant.BASE_URL_FACT) private val apiService: ApiService) :
    ViewModel() {

    val factsList = MutableLiveData<Facts>()

    val factList = MutableLiveData<Fact>()

    fun getFactsList(type: String) {
        viewModelScope.launch {
            val facts = apiService.getFacts(type)
            factsList.value = facts
        }
    }
    fun getFactList() {
        viewModelScope.launch {
            val fact = apiService.getFact()
            factList.value = fact
        }
    }
}