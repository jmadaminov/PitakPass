package com.badcompany.pitakpass.ui.driver_post.jump_in

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.PassengerOffer
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.model.Place
import com.badcompany.pitakpass.domain.repository.DriverPostRepository
import com.badcompany.pitakpass.domain.usecases.CreatePassengerPost
import com.badcompany.pitakpass.domain.usecases.GetActivePassengerPost
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.core.enums.EPostStatus
import com.badcompany.pitakpass.util.*
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi

class JumpInViewModel @ViewModelInject constructor(private val repository: DriverPostRepository,
                                                   private val createPassengerPost: CreatePassengerPost,
                                                   private val getActivePassengerPost: GetActivePassengerPost
) :
    BaseViewModel() {

    val isOffering = MutableLiveData<Boolean>()
    val hasFinished = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val offeringPostId = MutableLiveData<Long>()

    fun joinARide(postId: Long,
                  myPrice: Int?,
                  message: String,
                  seats: Int,
                  driverPost: DriverPostViewObj) {
        isOffering.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (offeringPostId.value == null) createPost(driverPost)
            sendAnOffer(postId, myPrice, message, seats)
        }

    }


    private suspend fun createPost(driverPost: DriverPostViewObj) {
        println("COBUG:  ${Thread.currentThread().name}")
        val placeFrom = Place(
            driverPost.from.districtId,
            driverPost.from.regionId,
            driverPost.from.lat,
            driverPost.from.lon,
            driverPost.from.regionName,
            driverPost.from.name,
        )
        val placeTo = Place(
            driverPost.to.districtId,
            driverPost.to.regionId,
            driverPost.to.lat,
            driverPost.to.lon,
            driverPost.to.regionName,
            driverPost.to.name,
        )
        val passengerPost = PassengerPost(null, placeFrom, placeTo, driverPost.price,
                                          driverPost.departureDate,
                                          driverPost.finishedDate,
                                          null,
                                          null,
                                          driverPost.timeFirstPart,
                                          driverPost.timeSecondPart,
                                          driverPost.timeThirdPart,
                                          driverPost.timeFourthPart,
                                          null,
                                          null,
                                          null,
                                          EPostStatus.CREATED,
                                          driverPost.seat,
                                          0
        )
        when (val response = createPassengerPost.execute(passengerPost)) {
            is ErrorWrapper.ResponseError -> {
                withContext(Dispatchers.Main) {
                    createResponse.value = response
                    errorMessage.value = response.message
                    isOffering.value = false
                }
            }
            is ErrorWrapper.SystemError -> {
                withContext(Dispatchers.Main) {
                    createResponse.value = response
                    errorMessage.value = response.err.localizedMessage
                    isOffering.value = false
                }
            }
            is ResultWrapper.Success -> {
                withContext(Dispatchers.Main) {
                    offeringPostId.value = response.value.id
                }
            }
            else -> {
            }
        }

    }


    private suspend fun sendAnOffer(postId: Long, myPrice: Int?, message: String, seats: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            println("COBUG:  ${Thread.currentThread().name}")
            val responseOfferCreate =
                repository.joinARide(PassengerOffer(postId, myPrice, message, seats,
                                                    offeringPostId.valueNN))
            withContext(Dispatchers.Main) {
                when (responseOfferCreate) {
                    is ResponseError -> {
                        errorMessage.value = responseOfferCreate.message
                        isOffering.value = false
                    }
                    is ResponseSuccess -> {
                        hasFinished.value = true
                        isOffering.value = false
                    }
                }.exhaustive
            }
        }
    }

    val createResponse = SingleLiveEvent<ResultWrapper<PassengerPost>>()
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

    fun setOfferingPost(id: Long?) {
        offeringPostId.value = id
    }


}