package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.PhotoBody
import java.io.File


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface FileUploadRemote {
    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody>

}