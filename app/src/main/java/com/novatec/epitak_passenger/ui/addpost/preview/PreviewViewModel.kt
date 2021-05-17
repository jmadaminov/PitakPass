package com.novatec.epitak_passenger.ui.addpost.preview

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.usecases.CreatePassengerPost
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.util.SingleLiveEvent
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
