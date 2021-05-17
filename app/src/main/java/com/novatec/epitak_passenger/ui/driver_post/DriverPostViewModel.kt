package com.novatec.epitak_passenger.ui.driver_post

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.domain.repository.DriverPostRepository
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.ResponseError
import com.novatec.epitak_passenger.util.ResponseSuccess
import com.novatec.epitak_passenger.util.SingleLiveEvent
import com.novatec.epitak_passenger.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DriverPostViewModel @Inject constructor(val repository: DriverPostRepository) :
    BaseViewModel() {

    val postData = SingleLiveEvent<DriverPost>()
    val errorMessage = SingleLiveEvent<String>()
    val isLoading = SingleLiveEvent<Boolean>()

    fun getPostById(id: Long) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDriverPostById(id)
            withContext(Dispatchers.Main) {
                isLoading.value = false
                when (response) {
                    is ResponseError -> errorMessage.value = response.message
                    is ResponseSuccess -> postData.value = response.value
                }.exhaustive
            }
        }
    }

}
