package com.novatec.pitakpass.data.repository

import com.novatec.pitakpass.domain.model.DriverPost
import com.novatec.pitakpass.domain.model.Filter
import com.novatec.pitakpass.domain.model.PassengerOffer
import com.novatec.pitakpass.remote.model.DriverPostsPagination
import com.novatec.pitakpass.remote.model.ObjRating
import com.novatec.pitakpass.util.ResponseWrapper

interface DriverPostRemote {

    suspend fun filterDriverPost(filter: Filter): ResponseWrapper<DriverPostsPagination>
    suspend fun getPostById(id: Long): ResponseWrapper<DriverPost>
    suspend fun joinARide(myOffer: PassengerOffer): ResponseWrapper<Any>
    suspend fun getMyRatingForDriver(id: Long): ResponseWrapper<ObjRating?>
    suspend fun editMyRatingForDriver(ratingId:Long,id: Long, rating : Float): ResponseWrapper<ObjRating?>
    suspend fun postMyRatingForDriver(id: Long, rating : Float): ResponseWrapper<ObjRating>
}