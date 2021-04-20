package com.novatec.pitakpass.data.source

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.data.repository.*
import com.novatec.pitakpass.domain.model.PhotoBody
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