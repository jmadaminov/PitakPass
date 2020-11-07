package com.badcompany.pitakpass.ui.main.mytrips.activetrips

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.usecases.DeletePassengerPost
import com.badcompany.pitakpass.domain.usecases.FinishPassengerPost
import com.badcompany.pitakpass.domain.usecases.GetActivePassengerPost
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.SingleLiveEvent
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