package com.novatec.pitakpass.ui.auth.register

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.User
import com.novatec.pitakpass.domain.usecases.RegisterUser
import com.novatec.pitakpass.ui.BaseViewModel
import com.novatec.pitakpass.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class RegisterViewModel  @Inject constructor(private val registerUser: RegisterUser) :
    BaseViewModel() {

    private val _registerForm = SingleLiveEvent<RegisterFormState>()
    val registerFormState: SingleLiveEvent<RegisterFormState> = _registerForm

    val regResponse = SingleLiveEvent<ResultWrapper<String>>()

    fun register(user: User) {
            regResponse.value = ResultWrapper.InProgress
            viewModelScope.launch(Dispatchers.IO)  {
                val response = registerUser.execute(user)
                withContext(Dispatchers.Main) {
                    regResponse.value = response
                }
            }
    }




}
