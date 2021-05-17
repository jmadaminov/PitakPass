package com.novatec.epitak_passenger.ui.auth.confirm

import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.App
import com.novatec.epitak_passenger.domain.model.AuthBody
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.domain.usecases.LogUserIn
import com.novatec.epitak_passenger.domain.usecases.SmsConfirm
import com.novatec.epitak_passenger.ui.BaseViewModel
import com.novatec.epitak_passenger.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

@HiltViewModel
class PhoneConfirmViewModel @Inject constructor(private val smsConfirm: SmsConfirm,
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
