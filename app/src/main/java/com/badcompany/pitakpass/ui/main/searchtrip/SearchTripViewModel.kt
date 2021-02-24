package com.badcompany.pitakpass.ui.main.searchtrip


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.badcompany.pitakpass.data.repository.PostFilterRepository
import com.badcompany.pitakpass.domain.model.*
import com.badcompany.pitakpass.domain.usecases.GetPlacesFeed
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.SingleLiveEvent
import com.badcompany.pitakpass.util.valueNN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class SearchTripViewModel @ViewModelInject constructor(val postFilterRepository: PostFilterRepository,
                                                       private val getPlacesFeed: GetPlacesFeed) :
    BaseViewModel() {

    private val _filter = MutableLiveData(Filter())
    val filter: LiveData<Filter> get() = _filter
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count


    var postOffers: LiveData<PagingData<DriverPost>> = MutableLiveData()
    fun getPassengerPost() {
        postOffers = postFilterRepository.getFilteredPosts(_filter.valueNN).cachedIn(viewModelScope)
    }


    private var fromFeedJob: Job? = null
    private var toFeedJob: Job? = null
    val fromPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()
    val toPlacesResponse = SingleLiveEvent<ResultWrapper<List<Place>>>()


    @ExperimentalSplittiesApi
    fun getPlacesFeed(queryString: String, isFrom: Boolean = true) {
        if (isFrom) fromPlacesResponse.value = ResultWrapper.InProgress
        else toPlacesResponse.value = ResultWrapper.InProgress
        resetFeedJobs()
        viewModelScope.launch(Dispatchers.IO + if (isFrom) fromFeedJob!! else toFeedJob!!) {
            val response = getPlacesFeed.execute(queryString)
            withContext(Dispatchers.Main) {
                if (isFrom) fromPlacesResponse.value = response
                else toPlacesResponse.value = response
            }
        }
    }

    private fun resetFeedJobs() {
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
        _filter.value = Filter()
    }


    fun applyFilter() {
        _filter.postValue(_filter.value)
        checkAppliedFiltersCount()
    }

    fun filterAC(isACChecked: Boolean) {
        if (isACChecked) _filter.valueNN.airConditioner = true
        else _filter.valueNN.airConditioner = null
    }

    fun setFilterPrices(minPrice: Int?, maxPrice: Int?) {
        _filter.valueNN.minPrice = minPrice
        _filter.valueNN.maxPrice = maxPrice
    }

    fun placeFromSelected(place: Place) {
        _filter.valueNN.fromRegionId = place.regionId
        _filter.valueNN.fromDistrictId = place.districtId
        applyFilter()
    }

    fun placeToSelected(place: Place) {
        _filter.valueNN.toRegionId = place.regionId
        _filter.valueNN.toDistrictId = place.districtId
        applyFilter()
    }

    fun setDate(dayOfMonth: Int, month: Int, year: Int) {
        val properMonthIndex = month + 1
        val monthString =
            if (properMonthIndex.toString().length == 1) "0$properMonthIndex" else properMonthIndex.toString()
        _filter.valueNN.departureDate = "$dayOfMonth.$monthString.$year"
        applyFilter()
    }

    fun dayTimePartsChecked(timeFirstPart: Boolean,
                            timeSecondPart: Boolean,
                            timeThirdPart: Boolean,
                            timeFourthPart: Boolean) {

        _filter.valueNN.timeFirstPart = timeFirstPart
        _filter.valueNN.timeSecondPart = timeSecondPart
        _filter.valueNN.timeThirdPart = timeThirdPart
        _filter.valueNN.timeFourthPart = timeFourthPart
    }

    fun seatCountChanged(count: Int) {
        _filter.valueNN.seat = count
    }


    private fun checkAppliedFiltersCount() {
        var appliedFilterCount = 0
        if (_filter.valueNN.minPrice != null && _filter.valueNN.maxPrice != null && (_filter.valueNN.minPrice != MIN_PRICE || _filter.valueNN.maxPrice != MAX_PRICE)) {
            appliedFilterCount++
        }
        if (_filter.valueNN.airConditioner == true) appliedFilterCount++
        if (_filter.valueNN.seat != null && _filter.valueNN.seat != 1) appliedFilterCount++
        if (_filter.valueNN.timeFirstPart == true || _filter.valueNN.timeSecondPart == true || _filter.valueNN.timeThirdPart == true || _filter.valueNN.timeFourthPart == true) appliedFilterCount++
        _count.value = appliedFilterCount
    }


}