package com.badcompany.pitakpass.ui.history_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.ResponseError
import com.badcompany.pitakpass.util.ResponseSuccess
import com.badcompany.pitakpass.util.SingleLiveEvent
import com.badcompany.pitakpass.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryPostViewModel @ViewModelInject constructor(val postRepository: PassengerPostRepository) :
    BaseViewModel() {

    val postData = MutableLiveData<PassengerPost>()

    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()

    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.getPassengerPostById(id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> errorMessage.value = response.message
                    is ResponseSuccess -> {
                        errorMessage.value = null
                        postData.value = response.value
                    }
                }.exhaustive
            }
        }
    }


}