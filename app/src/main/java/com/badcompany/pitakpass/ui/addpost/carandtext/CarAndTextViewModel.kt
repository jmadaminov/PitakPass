package com.badcompany.pitakpass.ui.addpost.carandtext

import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.CarDetails
import com.badcompany.pitakpass.domain.usecases.GetCars
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.AppPreferences
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


class CarAndTextViewModel @Inject constructor(private val getCars: GetCars) :
    BaseViewModel() {

    val carsResponse = SingleLiveEvent<ResultWrapper<List<CarDetails>>>()


    @ExperimentalSplittiesApi
    fun getCars() {
        carsResponse.value = ResultWrapper.InProgress

        viewModelScope.launch(Dispatchers.IO) {
            val response = getCars.execute(AppPreferences.token)
            withContext(Main) {
               carsResponse.value = response
            }
        }
    }


}
