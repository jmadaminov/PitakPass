package com.novatec.epitak_passenger.ui.auth.login

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.domain.usecases.LogUserIn
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.ResponseWrapper
import com.novatec.epitak_passenger.util.SingleLiveEvent
import com.novatec.epitak_passenger.util.numericOnly
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel
class LoginViewModel @Inject constructor(private val logUserIn: LogUserIn) :
    BaseViewModel() {

    val loginResponse = SingleLiveEvent<ResponseWrapper<UserCredentials?>>()
    val isLoading = SingleLiveEvent<Boolean>()

    var phoneNum = ""


    fun login(phoneNum: String) {
        this.phoneNum = phoneNum
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = logUserIn.execute(phoneNum.numericOnly())
            withContext(Main) {
                isLoading.value = false
                loginResponse.value = response
            }
        }
    }

//    // A placeholder username validation check
//    private fun isPhoneValid(phoneNum: String): Boolean {
//        return if (phoneNum.numericOnly().length == 12) true else {
//            _loginForm.value = LoginFormState(phoneError = R.string.incorrect_phone_number_format)
//            false
//        }
//    }

//    // A placeholder password validation check
//    private fun isPasswordValid(password: String): Boolean {
//        return password.length > 5
//    }
}
