package com.novatec.pitakpass.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatec.pitakpass.App
import com.novatec.pitakpass.domain.repository.UserRepository
import com.novatec.pitakpass.ui.BaseViewModel
import com.novatec.pitakpass.util.ResponseError
import com.novatec.pitakpass.util.ResponseSuccess
import com.novatec.pitakpass.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by jahon on 13-Apr-20
 */
@HiltViewModel
class MainViewModel  @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    val isAppVersionDeprecated = MutableLiveData<Boolean>()

    fun getActiveAppVersions() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository.getActiveAppVersions()

            when (response) {
                is ResponseError -> {
                }
                is ResponseSuccess -> {
                    withContext(Dispatchers.Main){
                        isAppVersionDeprecated.value = !response.value.contains(App.versionName)
                    }
                }
            }.exhaustive
        }


    }


}
