package com.novatec.epitak_passenger.ui.feedback

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.repository.UserRepository
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.ResponseError
import com.novatec.epitak_passenger.util.ResponseSuccess
import com.novatec.epitak_passenger.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class FeedbackViewModel @Inject constructor(val userRepository: UserRepository) :
    BaseViewModel() {

    val feedbackResponse = MutableLiveData<String>()
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
                        feedbackResponse.value = ""
                    }
                }.exhaustive
            }
        }
    }

}
