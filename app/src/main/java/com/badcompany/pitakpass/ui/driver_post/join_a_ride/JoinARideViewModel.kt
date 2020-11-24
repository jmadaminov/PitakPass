package com.badcompany.pitakpass.ui.driver_post.join_a_ride

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.PassengerOffer
import com.badcompany.pitakpass.domain.repository.DriverPostRepository
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.ResponseError
import com.badcompany.pitakpass.util.ResponseSuccess
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JoinARideViewModel @ViewModelInject constructor(private val repository: DriverPostRepository) :
    BaseViewModel() {

    val isOffering = MutableLiveData<Boolean>()
    val hasFinished = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    fun joinARide(postId: Long, myPrice: Int?, message: String) {
        isOffering.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.joinARide(PassengerOffer(postId, myPrice, message))
            withContext(Dispatchers.Main) {
//                when (response) {
//                    is ResponseError -> {
//                        errorMessage.value = response.message
//                        isOffering.value = false
//                    }
//                    is ResponseSuccess -> {
//                        hasFinished.value = true
//                        isOffering.value = false
//                    }
//                }.exhaustive
            }
        }

    }


}