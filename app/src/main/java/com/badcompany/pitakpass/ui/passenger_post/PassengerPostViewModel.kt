package com.badcompany.pitakpass.ui.passenger_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.domain.usecases.DeletePassengerPost
import com.badcompany.pitakpass.domain.usecases.FinishPassengerPost
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class PassengerPostViewModel @ViewModelInject constructor(val postRepository: PassengerPostRepository,
                                                          val deletePost: DeletePassengerPost,
                                                          val finishPost: FinishPassengerPost) :
    BaseViewModel() {

    val postData = SingleLiveEvent<PassengerPost>()
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


    val deletePostReponse = SingleLiveEvent<ResultWrapper<Unit>>()
    val finishPostResponse = SingleLiveEvent<ResultWrapper<Unit>>()

    @ExperimentalSplittiesApi
    fun deletePost(identifier: String) {
        isLoading.value = true
        deletePostReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = deletePost.execute(identifier)

            withContext(Dispatchers.Main) {
                deletePostReponse.value = response
            }
        }
    }

    @ExperimentalSplittiesApi
    fun finishPost(identifier: String) {
        isLoading.value = true
        finishPostResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = finishPost.execute(identifier)

            withContext(Dispatchers.Main) {
                finishPostResponse.value = response
            }
        }
    }

}
