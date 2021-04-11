package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.domain.model.*
import com.badcompany.pitakpass.remote.model.*
import com.badcompany.pitakpass.util.AppPrefs
import com.badcompany.pitakpass.util.RespFormatter
import retrofit2.http.*
import splitties.experimental.ExperimentalSplittiesApi

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
@ExperimentalSplittiesApi
interface AuthorizedApiService {


    //Passenger POST API

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("driver_post/action/filter")
    suspend fun filterDriverPost(@Body filter: Filter,
                                 @Query("page") page: Int = 0,
                                 @Query("size") size: Int = 10,
                                 @Header("Accept-Language") lang: String = AppPrefs.language): RespFormatter<DriverPostsPagination>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("feedback/action")
    suspend fun sendFeedback(@Body body: FeedbackBody): RespFormatter<Any>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("driver_post/action/{id}/as_passenger")
    suspend fun getDriverPostById(@Path(value = "id", encoded = true) identifier: Long,
                                  @Header("Accept-Language") lang: String = AppPrefs.language): RespFormatter<DriverPost>

    //END PASSENGER POST API


    //DRIVER POST API

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("passenger_post/action")
    suspend fun createPost(@Body passengerPostBody: PassengerPost,
                           @Header("Accept-Language") lang: String = AppPrefs.language): RespFormatter<PassengerPost>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("passenger_post/action/{id}")
    suspend fun getPassengerPostById(@Path(value = "id",
                                           encoded = true) id: Long,
                                     @Header("Accept-Language") lang: String = AppPrefs.language
    ): RespFormatter<PassengerPost>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/passenger/post/{id}")
    suspend fun getOffersForPost(@Path(value = "id", encoded = true) id: Long,
                                 @Query("page") page: Int = 0,
                                 @Query("size") size: Int = 10,
                                 @Header("Accept-Language") lang: String = AppPrefs.language
    ): RespFormatter<List<OfferDTO>>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("passenger_post/action/cancel/{identifier}")
    suspend fun deletePost(@Path(value = "identifier",
                                 encoded = true) identifier: String,
                           @Header("Accept-Language") lang: String = AppPrefs.language): PlainResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("passenger_post/action/finish/{identifier}")
    suspend fun finishPost(@Path(value = "identifier",
                                 encoded = true) identifier: String,
                           @Header("Accept-Language") lang: String = AppPrefs.language): PlainResponse


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("passenger_post/action/active")
    suspend fun getActivePosts(@Header("Accept-Language") lang: String = AppPrefs.language): PassengerActivePostsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("passenger_post/action/history")
    suspend fun getHistoryPosts(@Query("page") page: Int = 0,
                                @Query("size") size: Int = 10,
                                @Header("Accept-Language") lang: String = AppPrefs.language): PassengerHistoryPostsResponse

    //Places Feed

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("region/action")
    suspend fun getPlacesFeed(@Query("query") query: String,
                              @Header("Accept-Language") lang: String = AppPrefs.language
    ): PlaceListResponse
    //PLACES END

    ///CAR API


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_model/action")
    suspend fun getCarModels(@Header("Accept-Language") lang: String = AppPrefs.language): CarModelsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_color/action")
    suspend fun getCarColors(@Header("Accept-Language") lang: String = AppPrefs.language): CarColorsResponse


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/main/accept/{id}")
    suspend fun acceptOffer(@Path(value = "id", encoded = true) id: Long,
                            @Header("Accept-Language") lang: String = AppPrefs.language
    ): RespFormatter<String?>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/main/reject/{id}")
    suspend fun rejectOffer(@Path(value = "id", encoded = true) id: Long,
                            @Header("Accept-Language") lang: String = AppPrefs.language
    ): RespFormatter<String?>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/main/cancel/{id}")
    suspend fun cancelMyOffer(@Path(value = "id", encoded = true) id: Long,
                              @Header("Accept-Language") lang: String = AppPrefs.language
    ): RespFormatter<String?>

    //END CAR API

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("offer/passenger/action")
    suspend fun joinARide(@Body myOfferBody: PassengerOffer): RespFormatter<Any>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("driver_rating/action/{id}")
    suspend fun getMyRatingForDriver(@Path(value = "id",
                                           encoded = true) id: Long): RespFormatter<ObjRating>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("driver_rating/action/{id}")
    suspend fun editMyRatingForDriver(@Path(value = "id", encoded = true) id: Long,
                                      @Body objRating: ObjRating): RespFormatter<ObjRating>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("driver_rating/action")
    suspend fun postMyRatingForDriver(@Body objRating: ObjRating): RespFormatter<ObjRating>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("prof/action/detail/mb")
    suspend fun updateUserInfo(@Body reqUpdateProfileInfo: ReqUpdateProfileInfo): RespFormatter<UserCredentials>


}

