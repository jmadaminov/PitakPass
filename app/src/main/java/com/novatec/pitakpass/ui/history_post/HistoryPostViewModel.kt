package com.novatec.pitakpass.ui.history_post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.domain.repository.DriverPostRepository
import com.novatec.pitakpass.domain.repository.PassengerPostRepository
import com.novatec.pitakpass.remote.model.ObjRating
import com.novatec.pitakpass.ui.BaseViewModel
import com.novatec.pitakpass.util.ResponseError
import com.novatec.pitakpass.util.ResponseSuccess
import com.novatec.pitakpass.util.SingleLiveEvent
import com.novatec.pitakpass.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HistoryPostViewModel @ViewModelInject constructor(val postRepository: PassengerPostRepository,
                                                        val driverPostRepository: DriverPostRepository) :
    BaseViewModel() {

    val postData = MutableLiveData<PassengerPost>()

    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()

    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(IO) {
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

    val myRating = MutableLiveData<ObjRating>()

    fun postMyRating(id: Long, rating: Float) {
        isPostingRating.value = true
        viewModelScope.launch(IO) {
            val response = driverPostRepository.postMyRatingForDriver(id, rating)
            withContext(Dispatchers.Main) {
                isPostingRating.value = false
                when (response) {
                    is ResponseError -> {
                    }
                    is ResponseSuccess -> {
                        myRating.value = response.value
                    }
                }.exhaustive
            }
        }
    }

    fun getMyRating(id: Long) {
        isPostingRating.value = true
        viewModelScope.launch(IO) {
            val response = driverPostRepository.getMyRatingForDriver(id)
            withContext(Dispatchers.Main) {
                isPostingRating.value = false
                when (response) {
                    is ResponseError -> {
                    }
                    is ResponseSuccess -> {
                        myRating.value = response.value
                    }
                }.exhaustive
            }
        }
    }

    val isPostingRating = MutableLiveData<Boolean>()
    fun editMyRating(id: Long, rating: Float) {
        isPostingRating.value = true
        viewModelScope.launch(IO) {
            val response =
                driverPostRepository.editMyRatingForDriver(myRating.value!!.id!!, id, rating)
            withContext(Dispatchers.Main) {
                isPostingRating.value = false
                when (response) {
                    is ResponseError -> {
                    }
                    is ResponseSuccess -> {
                        myRating.value!!.rating = rating
                        myRating.value = myRating.value
                    }
                }.exhaustive
            }
        }
    }


}