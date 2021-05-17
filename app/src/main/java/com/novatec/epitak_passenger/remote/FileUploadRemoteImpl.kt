package com.novatec.epitak_passenger.remote

import com.novatec.epitak_passenger.data.repository.FileUploadRemote
import com.novatec.epitak_passenger.domain.model.PhotoBody
import com.novatec.epitak_passenger.util.ErrorWrapper
import com.novatec.epitak_passenger.util.ResultWrapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class FileUploadRemoteImpl @Inject constructor(private val apiService: ApiService) :
    FileUploadRemote {


    override suspend fun uploadPhoto(file: File): ResultWrapper<PhotoBody> {
        return try {
            val requestFile = file.asRequestBody("image/jpg".toMediaType())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = apiService.uploadPhoto(body)
            if (response.code == 1) ResultWrapper.Success(response.data!!)
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

}