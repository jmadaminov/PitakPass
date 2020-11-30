package com.badcompany.pitakpass.ui.feedback

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.ResponseError
import com.badcompany.pitakpass.util.ResponseSuccess
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.exhaustive
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedbackViewModel @ViewModelInject constructor(val userRepository: UserRepository) :
    BaseViewModel() {

    val feedbackResponse = MutableLiveData<ResponseWrapper<String>>()
    val errorString = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun sendFeedback(feedback: String) {
        isLoading.value = true
        viewModelScope.launch(IO) {
            val resp = userRepository.sendFeedback(feedback)
            withContext(Main) {
                isLoading.value = false
                when (resp) {
                    is ResponseError -> {
                        errorString.value = resp.message
                    }
                    is ResponseSuccess -> {

                    }
                }.exhaustive
            }
        }
    }

}
