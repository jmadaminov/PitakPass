package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.domain.model.*
import com.badcompany.pitakpass.remote.model.*
import com.badcompany.pitakpass.util.AppPrefs
import com.badcompany.pitakpass.util.RespFormatter
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface ApiService {

//    @GET("team.json")
//    suspend fun getBufferoos(): Flowable<BufferooResponse>

    //Passenger POST API

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("driver_post/action/filter")
    suspend fun filterDriverPost(@Header("Authorization") token: String,
                                 @Header("Accept-Language") lang: String,
                                 @Body filter: Filter): DriverPostsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("driver_post/action/{id}")
    suspend fun getDriverPostById(
        @Path(value = "id", encoded = true) identifier: Int,
        @Header("Accept-Language") lang: String = AppPrefs.language,
        @Header("Authorization") token: String = AppPrefs.token): RespFormatter<DriverPost>

    //END PASSENGER POST API


    //DRIVER POST API

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("passenger_post/action")
    suspend fun createPost(@Header("Authorization") token: String,
                           @Body passengerPostBody: PassengerPost): PlainResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("passenger_post/action/cancel/{identifier}")
    suspend fun deletePost(@Header("Authorization") token: String,
                           @Path(value = "identifier",
                                 encoded = true) identifier: String): PlainResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("passenger_post/action/finish/{identifier}")
    suspend fun finishPost(@Header("Authorization") token: String,
                           @Path(value = "identifier",
                                 encoded = true) identifier: String): PlainResponse


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("passenger_post/action/active")
    suspend fun getActivePosts(@Header("Authorization") token: String,
                               @Header("Accept-Language") lang: String): PassengerActivePostsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("passenger_post/action/history")
    suspend fun getHistoryPosts(@Header("Authorization") token: String,
                                @Header("Accept-Language") lang: String,
                                @Query("page") page: Int = 0,
                                @Query("size") size: Int = 10): PassengerHistoryPostsResponse

    //


    //Places Feed

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("region/action")
    suspend fun getPlacesFeed(@Header("Authorization") token: String,
                              @Header("Accept-Language") lang: String,
                              @Query("query") query: String): PlaceListResponse
    //PLACES END


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

    ///CAR API

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_model/action")
    suspend fun getCarModels(@Header("Authorization") token: String,
                             @Header("Accept-Language") lang: String): CarModelsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_color/action")
    suspend fun getCarColors(@Header("Authorization") token: String,
                             @Header("Accept-Language") lang: String): CarColorsResponse

    //END CAR API


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

