package com.badcompany.pitakpass.ui.edit_profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badcompany.pitakpass.domain.model.PhotoBody
import com.badcompany.pitakpass.domain.repository.FileUploadRepository
import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.util.ResultWrapper
import kotlinx.coroutines.launch
import java.io.File


class EditProfileViewModel @ViewModelInject constructor(private val userRepository: UserRepository,
                                                        private val fileUploadRepository: FileUploadRepository) :
    ViewModel() {


    var uploadedAvatarId: Long? = null


    fun updateProfile(name: String, surName: String) {
        viewModelScope.launch {
            userRepository.updateUserInfo(name, surName, uploadedAvatarId)
        }
    }

    var _uploadPhotoResp = MutableLiveData<ResultWrapper<PhotoBody>>()
    val uploadPhotoResp: LiveData<ResultWrapper<PhotoBody>> get() = _uploadPhotoResp

    fun uploadAvatar(file: File) {
        viewModelScope.launch {
            _uploadPhotoResp.value = fileUploadRepository.uploadPhoto(file)
        }
    }

}