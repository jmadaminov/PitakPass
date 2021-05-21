package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.domain.model.PhotoBody
import com.novatec.epitak_passenger.util.ResultWrapper
import java.io.File


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface FileUploadDataStore {

    suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoBody>

}