package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.domain.model.FeedbackBody
import com.badcompany.pitakpass.domain.model.User
import com.badcompany.pitakpass.domain.model.UserCredentials
import com.badcompany.pitakpass.remote.model.AuthResponse
import com.badcompany.pitakpass.remote.model.AuthSuccessResponse
import com.badcompany.pitakpass.remote.model.LoginRequest
import com.badcompany.pitakpass.remote.model.PhotoUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface ApiService {

    //AUTH API
    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/auth")
    suspend fun userLogin(@Body loginReq: LoginRequest): AuthResponse


    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/reg")
    suspend fun userRegister(@Body user: User): AuthResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("prof/mb/confirm")
    suspend fun smsConfirm(@Body user: UserCredentials): AuthSuccessResponse
    //END AUTH API


    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("feedback/action")
    suspend fun sendFeedback(@Body body: FeedbackBody): Response<Any>

    //FILE UPLOAD API

    @Headers("Accept: application/json")
    @Multipart
    @POST("attach/image")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse

    //END FILE UPLOAD API


//    class BufferooResponse {
//        lateinit var team: List<UserModel>
//    }

}

