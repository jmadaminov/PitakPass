package com.novatec.epitak_passenger.ui.main.mytrips.historytrips

import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HistoryTripsViewModel @Inject constructor(val historyPostRepository: HistoryPostRepository) :
    BaseViewModel() {



    var postOffers: LiveData<PagingData<PassengerPost>> = MutableLiveData()
    fun getHistoryPost() {
        postOffers = historyPostRepository.getHistoryPosts().cachedIn(viewModelScope)
    }


}