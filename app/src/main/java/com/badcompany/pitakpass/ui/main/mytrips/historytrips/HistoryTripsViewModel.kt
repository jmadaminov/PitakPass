package com.badcompany.pitakpass.ui.main.mytrips.historytrips

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.usecases.GetHistoryPassengerPost
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class HistoryTripsViewModel @ViewModelInject constructor(val getHistoryPassengerPost: GetHistoryPassengerPost) :
    BaseViewModel() {

    val historyPostsResponse = SingleLiveEvent<ResultWrapper<List<PassengerPost>>>()
    val cancelOrderReponse = SingleLiveEvent<ResultWrapper<String>>()
    val updateOrderReponse = SingleLiveEvent<ResultWrapper<String>>()
    var currentPage = 0

    @ExperimentalSplittiesApi
    fun getHistoryPosts(page: Int) {
        currentPage = page
        historyPostsResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                getHistoryPassengerPost.execute(hashMapOf(Pair(Constants.TXT_PAGE, page)))

            withContext(Dispatchers.Main) {
                historyPostsResponse.value = response
            }

        }

    }

}