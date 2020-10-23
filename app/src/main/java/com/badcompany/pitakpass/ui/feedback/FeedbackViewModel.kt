package com.badcompany.pitakpass.ui.feedback

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.data.repository.UserRemote
import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FeedbackViewModel @ViewModelInject constructor(val userRepository:UserRepository) : BaseViewModel() {

    val feedbackResponse = SingleLiveEvent<ResultWrapper<String>>()
    val errorString = SingleLiveEvent<String>()


    fun sendFeedback(feedback: String) {
        viewModelScope.launch(IO) {
//            userRepository.addOrUpdateUserCar()
        }
    }

}
