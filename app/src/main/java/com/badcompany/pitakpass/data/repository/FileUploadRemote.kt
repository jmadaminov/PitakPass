package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.CarColorEntity
import com.badcompany.pitakpass.data.model.CarEntity
import com.badcompany.pitakpass.data.model.CarModelEntity
import com.badcompany.pitakpass.data.model.PhotoEntity
import java.io.File


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface FileUploadRemote {
    suspend fun uploadPhoto(file: File): ResultWrapper<PhotoEntity>

}