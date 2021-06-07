package com.novatec.epitak_passenger.ui.addpost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.model.PhotoBody
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.domain.repository.FileUploadRepository
import com.novatec.epitak_passenger.domain.usecases.CreatePassengerPost
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.util.SingleLiveEvent
import com.novatec.epitak_passenger.viewobjects.ImageViewObj
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import splitties.experimental.ExperimentalSplittiesApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val createPassengerPost: CreatePassengerPost,
    private val fileUploadRepository: FileUploadRepository
) :
    BaseViewModel() {


//    var parcelImage: ImageViewObj? = null
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    val calendar = Calendar.getInstance()

    var isEditing: Boolean = false
    var id: Long? = null
    var placeFrom: Place? = null
    var placeTo: Place? = null
    var departureDate: String = dateFormat.format(calendar.time)
    var timeFirstPart = true
    var timeSecondPart = true
    var timeThirdPart = true
    var timeFourthPart = true
    var price: Int? = null
    var seat: Int = 1
//    var note: String? = null

    var pkgPhotoBody: PhotoBody? = null

    val createResponse = SingleLiveEvent<ResultWrapper<PassengerPost?>>()


    fun setDate(dayOfMonth: Int, month: Int, year: Int) {
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        departureDate = dateFormat.format(calendar.time)
    }


    @ExperimentalSplittiesApi
    fun createPassengerPost(driverPost: PassengerPost) {
        createResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = createPassengerPost.execute(driverPost)
            createResponse.postValue(response)
        }
    }


    var uploadPhotoResp = MutableLiveData<ResultWrapper<PhotoBody>>()
    fun uploadParcelImage(byteArray: ByteArray) {
        uploadPhotoResp.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = fileUploadRepository.uploadPhoto(byteArray)
            (response as? ResultWrapper.Success<PhotoBody>)?.value?.let {
                pkgPhotoBody = it
            }
            uploadPhotoResp.postValue(response)
        }
    }
}