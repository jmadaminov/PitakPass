package com.badcompany.pitakpass.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.App
import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.ResponseError
import com.badcompany.pitakpass.util.ResponseSuccess
import com.badcompany.pitakpass.util.exhaustive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by jahon on 13-Apr-20
 */
class MainViewModel  @ViewModelInject constructor(private val userRepository: UserRepository) : BaseViewModel() {

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
