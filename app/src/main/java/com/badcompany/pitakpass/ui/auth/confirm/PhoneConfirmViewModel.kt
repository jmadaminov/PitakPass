package com.badcompany.pitakpass.ui.auth.confirm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.App
import com.badcompany.pitakpass.domain.model.AuthBody
import com.badcompany.pitakpass.domain.model.UserCredentials
import com.badcompany.pitakpass.domain.usecases.SmsConfirm
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.SingleLiveEvent
import com.badcompany.pitakpass.util.numericOnly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PhoneConfirmViewModel @ViewModelInject constructor(private val smsConfirm: SmsConfirm) :
    BaseViewModel() {
    val confirmResponse = SingleLiveEvent<ResultWrapper<AuthBody>>()

    fun confirm(phone: String, code: String) {
        confirmResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(Dispatchers.IO) {
            val response = smsConfirm.execute(UserCredentials(phone.numericOnly(), code, App.uuid))
            withContext(Main) {
                confirmResponse.value = response
            }
        }

    }

    // A placeholder username validation check
    private fun isCodeValid(code: String): Boolean {
        return code.length == 5
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
