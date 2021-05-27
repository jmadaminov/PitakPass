package com.novatec.epitak_passenger.ui.passenger_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novatec.epitak_passenger.data.repository.PostOffersRepository
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.repository.PassengerPostRepository
import com.novatec.epitak_passenger.domain.usecases.DeletePassengerPost
import com.novatec.epitak_passenger.domain.usecases.FinishPassengerPost
import com.novatec.epitak_passenger.remote.model.OfferDTO
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

import javax.inject.Inject
@HiltViewModel
class PassengerPostViewModel @Inject constructor(val postRepository: PassengerPostRepository,
                                                          val postOffersRepository: PostOffersRepository,
                                                          val deletePost: DeletePassengerPost,
                                                          val finishPost: FinishPassengerPost) :
    BaseViewModel() {

    val postData = MutableLiveData<PassengerPost>()
    val errorMessage = SingleLiveEvent<String?>()
    val isLoading = SingleLiveEvent<Boolean>()
    val offerActionLoading = SingleLiveEvent<Boolean>()

    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.getPassengerPostById(id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> errorMessage.value = response.message
                    is ResponseSuccess -> {
                        errorMessage.value = null
                        postData.value = response.value
                    }
                }.exhaustive
            }
        }
    }

     var postOffers: LiveData<PagingData<OfferDTO>>?=null
    fun getOffersForPost(id: Long) {
        postOffers = postOffersRepository.getOffersForPost(id).cachedIn(viewModelScope)
    }

    val deletePostReponse = SingleLiveEvent<ResultWrapper<Unit>>()
    val finishPostResponse = SingleLiveEvent<ResultWrapper<Unit>>()

    @ExperimentalSplittiesApi
    fun deletePost(identifier: String) {
        isLoading.value = true
        deletePostReponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = deletePost.execute(identifier)

            withContext(Dispatchers.Main) {
                isLoading.value = false
                deletePostReponse.value = response
            }
        }
    }

    @ExperimentalSplittiesApi
    fun finishPost(identifier: String) {
        isLoading.value = true
        finishPostResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = finishPost.execute(identifier)

            withContext(Dispatchers.Main) {
                isLoading.value = false
                finishPostResponse.value = response
            }
        }
    }

    val offerActionResp = SingleLiveEvent<String>()
    val offerActionError = SingleLiveEvent<String>()
    fun acceptOffer(id: Long) {

        offerActionLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.acceptOffer(id)

            withContext(Dispatchers.Main) {
                offerActionLoading.value = false
                when (response) {
                    is ResponseError -> offerActionError.value = response.message
                    is ResponseSuccess -> {
                        offerActionError.value = null
                        offerActionResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }

    fun cancelOffer(id: Long) {

        offerActionLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = postRepository.rejectOffer(id)
            withContext(Dispatchers.Main) {
                offerActionLoading.value = false
                when (response) {
                    is ResponseError -> offerActionError.value = response.message
                    is ResponseSuccess -> {
                        offerActionError.value = null
                        offerActionResp.value = response.value
                    }
                }.exhaustive
            }
        }
    }

}
