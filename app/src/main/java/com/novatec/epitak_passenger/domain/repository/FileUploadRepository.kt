package com.novatec.epitak_passenger.domain.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.PhotoBody
import java.io.File

interface FileUploadRepository {

    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody>

}