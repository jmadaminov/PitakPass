package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.PhotoEntity
import com.badcompany.pitakpass.data.repository.FileUploadRemote
import com.badcompany.pitakpass.remote.mapper.PhotoMapper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class FileUploadRemoteImpl @Inject constructor(private val apiService: ApiService,
                                               private val photoMapper: PhotoMapper) :
    FileUploadRemote {


    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoEntity> {
        return try {
            val requestFile = RequestBody.create(MediaType.parse("image/jpg"), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = apiService.uploadPhoto(body)
            if (response.code == 1) ResultWrapper.Success(photoMapper.mapToEntity(response.data!!))
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

}