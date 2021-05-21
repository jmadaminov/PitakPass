package com.novatec.epitak_passenger.domain.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.PhotoBody

interface FileUploadRepository {

    suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoBody>

}