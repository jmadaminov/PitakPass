package com.badcompany.pitakpass.ui.auth.register

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.User
import com.badcompany.pitakpass.domain.usecases.RegisterUser
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel  @ViewModelInject constructor(private val registerUser: RegisterUser) :
    BaseViewModel() {

    private val _registerForm = SingleLiveEvent<RegisterFormState>()
    val registerFormState: SingleLiveEvent<RegisterFormState> = _registerForm

    val response = SingleLiveEvent<ResultWrapper<String>>()

    fun register(user: User) {
            response.value = ResultWrapper.InProgress
            viewModelScope.launch(Dispatchers.IO)  {
                response.value = registerUser.execute(user)
            }
    }




}
