package com.novatec.epitak_passenger.ui.addpost.destinations

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.domain.usecases.GetPlacesFeed
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi


@HiltViewModel
class DestinationsViewModel @Inject constructor(private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {

    var placeFrom: Place? = null
    var placeTo: Place? = null

    private var fromFeedJob: Job? = null
    private var toFeedJob: Job? = null
    val placesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()

    var preSelectedPlace: Place? = null

    @ExperimentalSplittiesApi
    fun getPlacesFeed(queryString: String, isFrom: Boolean = true) {
        placesResponse.value = ResultWrapper.InProgress
        resetFromFeedJob()
        viewModelScope.launch(Dispatchers.IO + if (isFrom) fromFeedJob!! else toFeedJob!!) {
            placesResponse.postValue(getPlacesFeed.execute(queryString))
        }
    }

    private fun resetFromFeedJob() {
        fromFeedJob?.cancel()
        fromFeedJob = Job()
        toFeedJob?.cancel()
        toFeedJob = Job()

    }

    override fun onCleared() {
        super.onCleared()
        fromFeedJob?.cancel()
        toFeedJob?.cancel()
    }

}
