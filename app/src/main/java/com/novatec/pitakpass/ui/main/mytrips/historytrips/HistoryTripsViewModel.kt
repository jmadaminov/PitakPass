package com.novatec.pitakpass.ui.main.mytrips.historytrips

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.ui.BaseViewModel

class HistoryTripsViewModel @ViewModelInject constructor(val historyPostRepository: HistoryPostRepository) :
    BaseViewModel() {



    var postOffers: LiveData<PagingData<PassengerPost>> = MutableLiveData()
    fun getHistoryPost() {
        postOffers = historyPostRepository.getHistoryPosts().cachedIn(viewModelScope)
    }


}