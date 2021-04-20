package com.novatec.pitakpass.ui.main.mytrips.activetrips

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.domain.usecases.DeletePassengerPost
import com.novatec.pitakpass.domain.usecases.FinishPassengerPost
import com.novatec.pitakpass.domain.usecases.GetActivePassengerPost
import com.novatec.pitakpass.ui.BaseViewModel
import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class ActiveTripsViewModel @ViewModelInject constructor(val getActivePassengerPost: GetActivePassengerPost) :
    BaseViewModel() {

    val activePostsResponse = SingleLiveEvent<ResultWrapper<List<PassengerPost>>>()

    @ExperimentalSplittiesApi
    fun getActivePosts() {
        activePostsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getActivePassengerPost.execute()
            withContext(Dispatchers.Main) {
                activePostsResponse.value = response
            }
        }
    }


}