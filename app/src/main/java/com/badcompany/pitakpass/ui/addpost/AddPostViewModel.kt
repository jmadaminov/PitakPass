package com.badcompany.pitakpass.ui.addpost

import com.badcompany.pitakpass.domain.model.CarDetails
import com.badcompany.pitakpass.domain.model.Place
import com.badcompany.pitakpass.App
import com.badcompany.pitakpass.ui.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import splitties.init.appCtx
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
class AddPostViewModel @Inject constructor() :
    BaseViewModel() {

    var isEditing: Boolean = false
    var id: Long? = null
    var placeFrom: Place? = null
    var placeTo: Place? = null
    var departureDate: String? = null
    var timeFirstPart = false
    var timeSecondPart = false
    var timeThirdPart = false
    var timeFourthPart = false
    var car: CarDetails? = null
    var price: Int? = null
    var seat: Int? = null
    var note: String? = null

//    val carImgResponse = SingleLiveEvent<ResultWrapper<PhotoBody>>()

//    @InternalCoroutinesApi
//    fun getCarColorsAndModels(token: String) {
//        viewModelScope.launch(IO) {
//            withContext(Main) {
//                colorsAndModels.value = ResultWrapper.InProgress
//            }
//            withContext(IO) {
//                try {
//                    val colors = async { getCarColors.execute(token) }
//                    val models = async { getCarModels.execute(token) }
//                    processResponses(colors.await(), models.await())
//                } catch (e: Exception) {
//                    withContext(Main) {
//                        colorsAndModels.value = ErrorWrapper.SystemError(e)
//                    }
//                }
//            }
//        }
//    }


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
//        (appCtx as App).releaseAddCarComponent()
    }
}