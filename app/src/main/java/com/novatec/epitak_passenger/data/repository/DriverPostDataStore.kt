package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.domain.model.Offer
import com.novatec.epitak_passenger.remote.model.ObjRating
import com.novatec.epitak_passenger.util.ResponseWrapper


interface DriverPostDataStore {

    suspend fun getPostById(id: Long): ResponseWrapper<DriverPost>
    suspend fun sendOffer(myOffer: Offer): ResponseWrapper<Any>
    suspend fun getMyRatingForDriver(id: Long): ResponseWrapper<ObjRating?>
    suspend fun editMyRatingForDriver(
        ratingId: Long,
        id: Long,
        rating: Float
    ): ResponseWrapper<ObjRating?>

    suspend fun postMyRatingForDriver(id: Long, rating: Float): ResponseWrapper<ObjRating>
}