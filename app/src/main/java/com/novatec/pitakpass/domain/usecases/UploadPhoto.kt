package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.PhotoBody
import com.novatec.pitakpass.domain.repository.FileUploadRepository
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