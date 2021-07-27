package com.novatec.epitak_passenger.ui.edit_profile

import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatec.epitak_passenger.domain.model.PhotoBody
import com.novatec.epitak_passenger.domain.repository.FileUploadRepository
import com.novatec.epitak_passenger.domain.repository.UserRepository
import com.novatec.epitak_passenger.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.preferences.edit


@HiltViewModel
class EditProfileViewModel @Inject constructor(private val userRepository: UserRepository,
                                                        private val fileUploadRepository: FileUploadRepository) :
    ViewModel() {

    private  var _isUpdating = MutableLiveData<Boolean>()
    val isUpdating: LiveData<Boolean> get() = _isUpdating

    private   var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private  var _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> get() = _updateSuccess

    fun updateProfile(name: String, surName: String) {
        _isUpdating.value = true
        viewModelScope.launch(IO) {
            val response = userRepository.updateUserInfo(name,
                                                         surName,
                                                         (uploadPhotoResp.value as? ResultWrapper.Success)?.value?.id)
            withContext(Main) {
                _isUpdating.value = false
                when (response) {
                    is ResponseError -> {
                        _errorMessage.value = response.message
                    }
                    is ResponseSuccess -> {
                        UserPrefs.edit {
                            this.name = name
                            this.surname = surName
                            (uploadPhotoResp.value as? ResultWrapper.Success)?.value?.link?.let {
                                this.avatar = it
                            }
                        }

                        _updateSuccess.value = true
                    }
                }.exhaustive
            }
        }
    }

   private var _uploadPhotoResp = MutableLiveData<ResultWrapper<PhotoBody>>()
    val uploadPhotoResp: LiveData<ResultWrapper<PhotoBody>> get() = _uploadPhotoResp

    fun uploadAvatar(byteArray: ByteArray) {
     _uploadPhotoResp.value = ResultWrapper.InProgress
        viewModelScope.launch(IO) {
            val response = fileUploadRepository.uploadPhoto(byteArray)
            withContext(Main) {
                _uploadPhotoResp.value = response
            }
        }
    }

}