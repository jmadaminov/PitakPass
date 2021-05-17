package com.novatec.epitak_passenger.ui.addpost

import com.novatec.epitak_passenger.domain.model.CarDetails
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.App
import com.novatec.epitak_passenger.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import splitties.init.appCtx
import javax.inject.Inject

/**
 * Created by jahon on 28-Apr-20
 */
@HiltViewModel
class AddPostViewModel  @Inject constructor() :
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
    var price: Int? = null
    var seat: Int? = null
    var note: String? = null


    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
//        (appCtx as App).releaseAddCarComponent()
    }
}