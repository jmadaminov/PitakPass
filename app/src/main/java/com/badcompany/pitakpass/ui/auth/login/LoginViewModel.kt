package com.badcompany.pitakpass.ui.auth.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.numericOnly
import com.badcompany.pitakpass.domain.usecases.LogUserIn
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginViewModel  @ViewModelInject constructor(private val logUserIn: LogUserIn) : BaseViewModel() {

    val loginResponse = SingleLiveEvent<ResultWrapper<String>>()

    var phoneNum = ""
    fun login(phoneNum: String) {
        this.phoneNum = phoneNum
        loginResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = logUserIn.execute(phoneNum.numericOnly())
            withContext(Main) {
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
