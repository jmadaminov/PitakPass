package com.novatec.epitak_passenger.data.source

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.data.repository.*
import com.novatec.epitak_passenger.domain.model.PhotoBody
import java.io.File
import javax.inject.Inject

/**
 * Implementation of the [UserDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class FileUploadRemoteDataStore @Inject constructor(private val fileUploadRemote: FileUploadRemote) :
    FileUploadDataStore {

    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody> {
        return fileUploadRemote.uploadPhoto(file)
    }



}