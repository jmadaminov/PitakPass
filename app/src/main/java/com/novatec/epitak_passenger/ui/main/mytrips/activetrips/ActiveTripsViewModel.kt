package com.novatec.epitak_passenger.ui.main.mytrips.activetrips

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.usecases.DeletePassengerPost
import com.novatec.epitak_passenger.domain.usecases.FinishPassengerPost
import com.novatec.epitak_passenger.domain.usecases.GetActivePassengerPost
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

@HiltViewModel
class ActiveTripsViewModel @Inject constructor(val getActivePassengerPost: GetActivePassengerPost) :
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