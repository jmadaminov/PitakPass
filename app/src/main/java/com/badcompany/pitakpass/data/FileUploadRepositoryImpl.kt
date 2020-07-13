package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.mapper.PhotoMapper
import com.badcompany.pitakpass.data.source.FileUploadDataStoreFactory
import com.badcompany.pitakpass.domain.domainmodel.PhotoBody
import com.badcompany.pitakpass.domain.repository.FileUploadRepository
import com.badcompany.pitakpass.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class FileUploadRepositoryImpl @Inject constructor(private val factory: FileUploadDataStoreFactory,
                                                   private val photoMapper: PhotoMapper
) : FileUploadRepository {

    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody> {
        val response = factory.retrieveDataStore(false)
            .uploadPhoto(file)
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> ResultWrapper.Success(photoMapper.mapFromEntity(response.value))
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }




}