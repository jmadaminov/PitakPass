package com.novatec.epitak_passenger.ui.bsd_offer_parcel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.core.enums.EPostStatus
import com.novatec.epitak_passenger.core.enums.EPostType
import com.novatec.epitak_passenger.domain.model.Offer
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.model.PhotoBody
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.domain.repository.DriverPostRepository
import com.novatec.epitak_passenger.domain.repository.FileUploadRepository
import com.novatec.epitak_passenger.domain.usecases.CreatePassengerPost
import com.novatec.epitak_passenger.domain.usecases.GetActivePassengerPost
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.*
import com.novatec.epitak_passenger.viewobjects.DriverPostViewObj
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject

@HiltViewModel
class OfferParcelViewModel @Inject constructor(
    private val repository: DriverPostRepository,
    private val fileUploadRepository: FileUploadRepository,
    private val createPassengerPost: CreatePassengerPost,
    private val getActivePassengerPost: GetActivePassengerPost
) :
    BaseViewModel() {

    val isOffering = MutableLiveData<Boolean>()
    val hasFinished = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    val offeringPostId = MutableLiveData<Long?>()

    fun offerParcel(postId: Long, myPrice: Int, message: String, driverPost: DriverPostViewObj) {
        isOffering.value = true
        viewModelScope.launch(Dispatchers.IO) {
            myPrice.let { driverPost.price = myPrice }
            driverPost.seat = 0
            if (offeringPostId.value == null) createPost(driverPost)
            sendParcelOffer(postId, myPrice, message)
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
        val imageList =
            (parcelImageResp.value as? ResultWrapper.Success)?.value.run { listOf(PhotoBody(this?.id)) }
        val passengerPost = PassengerPost(
            null, placeFrom, placeTo, driverPost.price,
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
            0,
            imageList = imageList,
            postType = EPostType.PASSENGER_PARCEL
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
                    offeringPostId.value = response.value!!.id
                }
            }
            else -> {
            }
        }

    }


    private suspend fun sendParcelOffer(postId: Long, myPrice: Int, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            println("COBUG:  ${Thread.currentThread().name}")
            val responseOfferCreate =
                repository.sendOffer(
                    Offer(
                        postId, myPrice, message, 0,
                        offeringPostId.value!!
                    )
                )
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

    fun setOfferingPost(id: Long) {
        offeringPostId.value = id
    }

    fun clearOfferingPost() {
        offeringPostId.value = null
    }

    var parcelImageResp = MutableLiveData<ResultWrapper<PhotoBody>>()

    fun uploadParcelImage(byteArray: ByteArray) {
        parcelImageResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = fileUploadRepository.uploadPhoto(byteArray)
            withContext(Dispatchers.Main) {
                parcelImageResp.value = response
            }
        }
    }

}