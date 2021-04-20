package com.novatec.pitakpass.ui.driver_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.novatec.pitakpass.domain.model.DriverPost
import com.novatec.pitakpass.domain.repository.DriverPostRepository
import com.novatec.pitakpass.ui.BaseViewModel
import com.novatec.pitakpass.util.ResponseError
import com.novatec.pitakpass.util.ResponseSuccess
import com.novatec.pitakpass.util.SingleLiveEvent
import com.novatec.pitakpass.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DriverPostViewModel @ViewModelInject constructor(val repository: DriverPostRepository) :
    BaseViewModel() {

    val postData = SingleLiveEvent<DriverPost>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()

    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDriverPostById(id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> errorMessage.value = response.message
                    is ResponseSuccess -> postData.value = response.value
                }.exhaustive
            }
        }
    }

}
