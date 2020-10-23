package com.badcompany.pitakpass.ui.main.searchtrip

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.model.Place
import com.badcompany.pitakpass.domain.usecases.GetDriverPostWithFilter
import com.badcompany.pitakpass.domain.usecases.GetPlacesFeed
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.AppPreferences
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

class SearchTripViewModel  @ViewModelInject constructor(val getDriverPostWithFilter: GetDriverPostWithFilter,
                                                        private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {


    val passengerPostsReponse = SingleLiveEvent<ResultWrapper<List<DriverPost>>>()
    var currentPage = 0
    var filter = Filter()

    @ExperimentalSplittiesApi
    fun getPassengerPost() {
        passengerPostsReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = getDriverPostWithFilter.execute(hashMapOf(
                Pair(Constants.TXT_TOKEN, AppPreferences.token),
                Pair(Constants.TXT_LANG, AppPreferences.language),
                Pair(Constants.TXT_FILTER, filter)))

            withContext(Dispatchers.Main) {
                passengerPostsReponse.value = response
            }

        }

    }


    private var fromFeedJob: Job? = null
    private var toFeedJob: Job? = null
    val fromPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()
    val toPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()


    @ExperimentalSplittiesApi
    fun getPlacesFeed(queryString: String, isFrom: Boolean = true) {
        if (isFrom) fromPlacesResponse.value = ResultWrapper.InProgress
        else toPlacesResponse.value = ResultWrapper.InProgress
        resetFromFeedJob(isFrom)
        viewModelScope.launch(Dispatchers.IO + if (isFrom) fromFeedJob!! else toFeedJob!!) {
            val response =
                getPlacesFeed.execute(hashMapOf(Pair(Constants.TXT_TOKEN,
                                                     AppPreferences.token),
                                                Pair(Constants.TXT_LANG,
                                                     AppPreferences.language),
                                                Pair(Constants.TXT_PLACE, queryString)))

            withContext(Dispatchers.Main) {
                if (isFrom) fromPlacesResponse.value = response
                else toPlacesResponse.value = response
            }
        }
    }

    private fun resetFromFeedJob(isFrom: Boolean) {
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

    fun resetFilter() {
        filter = Filter()
    }



}