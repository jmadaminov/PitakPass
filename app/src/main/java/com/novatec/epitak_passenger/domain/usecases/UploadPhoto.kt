package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.PhotoBody
import com.novatec.epitak_passenger.domain.repository.FileUploadRepository
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