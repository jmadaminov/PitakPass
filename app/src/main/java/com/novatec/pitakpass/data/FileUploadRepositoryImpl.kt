package com.novatec.pitakpass.data

import com.novatec.pitakpass.data.source.FileUploadDataStoreFactory
import com.novatec.pitakpass.domain.model.PhotoBody
import com.novatec.pitakpass.domain.repository.FileUploadRepository
import com.novatec.pitakpass.domain.repository.UserRepository
import com.novatec.pitakpass.util.ErrorWrapper
import com.novatec.pitakpass.util.ResultWrapper
import java.io.File
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class FileUploadRepositoryImpl @Inject constructor(private val factory: FileUploadDataStoreFactory) :
    FileUploadRepository {

    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody> {
        val response = factory.retrieveDataStore(false)
            .uploadPhoto(file)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> ResultWrapper.Success(response.value)
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}