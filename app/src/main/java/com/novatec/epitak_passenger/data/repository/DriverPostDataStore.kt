package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.domain.model.Filter
import com.novatec.epitak_passenger.domain.model.PassengerOffer
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.remote.model.ObjRating
import com.novatec.epitak_passenger.util.ResponseWrapper
import com.novatec.epitak_passenger.util.ResultWrapper


interface DriverPostDataStore {

//    suspend fun filterDriverPost(filter: Filter): ResultWrapper<List<DriverPost>>
    suspend fun getPostById(id: Long): ResponseWrapper<DriverPost>
    suspend fun joinARide(myOffer: PassengerOffer): ResponseWrapper<Any>
    suspend fun getMyRatingForDriver(id: Long): ResponseWrapper<ObjRating?>
    suspend fun editMyRatingForDriver(ratingId:Long,id: Long, rating : Float): ResponseWrapper<ObjRating?>
    suspend fun postMyRatingForDriver(id: Long, rating : Float): ResponseWrapper<ObjRating>
}