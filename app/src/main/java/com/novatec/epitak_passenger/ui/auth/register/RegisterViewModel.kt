package com.novatec.epitak_passenger.ui.auth.register

import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.domain.usecases.RegisterUser
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.SingleLiveEvent
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
