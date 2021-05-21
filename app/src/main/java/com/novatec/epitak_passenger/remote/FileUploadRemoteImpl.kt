package com.novatec.epitak_passenger.remote

import com.novatec.epitak_passenger.data.repository.FileUploadRemote
import com.novatec.epitak_passenger.domain.model.PhotoBody
import com.novatec.epitak_passenger.util.ErrorWrapper
import com.novatec.epitak_passenger.util.ResultWrapper
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class FileUploadRemoteImpl @Inject constructor(private val apiService: ApiService) :
    FileUploadRemote {


    override suspend fun uploadPhoto(bytes: ByteArray): ResultWrapper<PhotoBody> {
        return try {

//            val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("file", file.name, RequestBody.create( "application/octet-stream".toMediaType(),file))
//                .build()

//            val map: MutableMap<String, RequestBody> = HashMap()
//            val bos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)

//            if (bos.size() > 0) {
//                val fileBody: RequestBody = bos.toByteArray().toRequestBody()
//                map["file\"; filename=\"pp.JPG\""] = fileBody
//            }
            val requestFile =bytes.toRequestBody("image/jpg".toMediaType())
            val body = MultipartBody.Part.createFormData("file", "", requestFile)
            val response = apiService.uploadPhoto(body)
//            val response = apiService.uploadPhoto(body)
            if (response.code == 1) ResultWrapper.Success(response.data!!)
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

}