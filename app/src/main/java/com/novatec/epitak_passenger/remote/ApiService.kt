package com.novatec.epitak_passenger.remote

import com.novatec.epitak_passenger.core.enums.EAppType
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.remote.model.*
import com.novatec.epitak_passenger.util.AppPrefs
import com.novatec.epitak_passenger.util.RespFormatter
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface ApiService {

    //AUTH API
    @POST("prof/mb/auth")
    suspend fun userLogin(
        @Body loginReq: LoginRequest,
        @Header("Accept-Language") lang: String = AppPrefs.language
    ): RespFormatter<UserCredentials>


    @POST("prof/mb/reg")
    suspend fun userRegister(
        @Body user: User,
        @Header("Accept-Language") lang: String = AppPrefs.language
    ): AuthResponse

    @POST("prof/mb/confirm")
    suspend fun smsConfirm(@Body user: UserCredentials): AuthSuccessResponse
    //END AUTH API


    //FILE UPLOAD API
    @Multipart
    @POST("attach/image")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): PhotoUploadResponse

    @GET("force_update/action/version")
    suspend fun getActiveAppVersions(
        @Query("appType") appType: String = EAppType.PASSENGER.name,
        @Query("platformType") platformType: String = "ANDROID"
    ): RespFormatter<List<IdVersionDTO>>


    //END FILE UPLOAD API


//    class BufferooResponse {
//        lateinit var team: List<UserModel>
//    }

}

