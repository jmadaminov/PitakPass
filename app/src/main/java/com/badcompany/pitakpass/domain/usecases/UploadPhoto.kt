package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.PhotoBody
import com.badcompany.pitakpass.domain.repository.FileUploadRepository
import java.io.File


/** User Login Use Case
 *
 */
class UploadPhoto(val repository: FileUploadRepository) :
    UseCaseWithParams<File, ResultWrapper<PhotoBody>>() {

    override suspend fun buildUseCase(params: File): ResultWrapper<PhotoBody> {
        return repository.uploadPhoto(params)
    }
}