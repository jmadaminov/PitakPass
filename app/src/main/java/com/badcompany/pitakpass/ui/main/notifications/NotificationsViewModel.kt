package com.badcompany.pitakpass.ui.main.notifications

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.util.Constants.TXT_ID
import com.badcompany.pitakpass.util.Constants.TXT_TOKEN
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.exhaustive
import com.badcompany.pitakpass.domain.model.CarDetails
import com.badcompany.pitakpass.ui.BaseViewModel
import com.badcompany.pitakpass.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NotificationsViewModel  @ViewModelInject constructor() :
    BaseViewModel() {



}