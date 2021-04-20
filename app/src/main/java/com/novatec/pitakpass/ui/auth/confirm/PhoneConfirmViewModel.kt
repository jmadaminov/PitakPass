package com.novatec.pitakpass.ui.auth.confirm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.pitakpass.App
import com.novatec.pitakpass.domain.model.AuthBody
import com.novatec.pitakpass.domain.model.UserCredentials
import com.novatec.pitakpass.domain.usecases.LogUserIn
import com.novatec.pitakpass.domain.usecases.SmsConfirm
import com.novatec.pitakpass.ui.BaseViewModel
import com.novatec.pitakpass.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main


@ObsoleteCoroutinesApi
class PhoneConfirmViewModel @ViewModelInject constructor(private val smsConfirm: SmsConfirm,
                                                         private val logUserIn: LogUserIn) :
    BaseViewModel() {

    companion object {
        const val REGAIN_CODE_INTERVAL = 60000L

    }

    val confirmResponse = SingleLiveEvent<ResultWrapper<AuthBody>>()

    fun confirm(phone: String, code: String) {
        confirmResponse.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = smsConfirm.execute(UserCredentials(phone.numericOnly(), code, App.uuid))
            withContext(Main) {
                confirmResponse.value = response
            }
        }

    }

    private val _requestSmsCountDown = MutableLiveData<Int>()
    val requestSmsCountDown: LiveData<Int> get() = _requestSmsCountDown

    var counterJob = Job()
    fun startTimer() {
        CoroutineScope(IO + counterJob).launchPeriodicAsync(1000,
                                                            REGAIN_CODE_INTERVAL) { timeLeft ->
            CoroutineScope(Main).launch {
                _requestSmsCountDown.value = (timeLeft / 1000).toInt()
            }
            if (timeLeft == 0L) {
                resetTimer()
            }
        }
    }

    fun resetTimer() {
        counterJob.cancel()
        counterJob = Job()
    }


    private var _respRegainCodeSuccess = MutableLiveData<ResponseWrapper<UserCredentials?>>()
    val respRegainCode: LiveData<ResponseWrapper<UserCredentials?>> get() = _respRegainCodeSuccess

    fun requestCodeAgain(phoneNum: String) {
        viewModelScope.launch(IO) {
            val response = logUserIn.execute(phoneNum.numericOnly())
            withContext(Main) {
                _respRegainCodeSuccess.value = response
            }
        }
    }

}
