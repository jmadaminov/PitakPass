package com.novatec.pitakpass.ui.addpost.preview

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.domain.usecases.CreatePassengerPost
import com.novatec.pitakpass.ui.BaseViewModel
import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi


@HiltViewModel
class PreviewViewModel @Inject constructor(private val createPassengerPost: CreatePassengerPost) :
    BaseViewModel() {

    val createResponse = SingleLiveEvent<ResultWrapper<PassengerPost?>>()


    @ExperimentalSplittiesApi
    fun createPassengerPost(driverPost: PassengerPost) {
        createResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = createPassengerPost.execute(driverPost)

            withContext(Main) {
                createResponse.value = response
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }


}
