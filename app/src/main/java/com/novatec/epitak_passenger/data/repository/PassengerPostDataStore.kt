package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.Filter
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.remote.model.OfferDTO
import com.novatec.epitak_passenger.util.ResponseWrapper


interface PassengerPostDataStore {

    suspend fun createPassengerPost( post: PassengerPost): ResultWrapper<PassengerPost?>
    suspend fun deletePassengerPost( identifier: String): ResultWrapper<Unit>
    suspend fun finishPassengerPost( identifier: String): ResultWrapper<Unit>
    suspend fun getActivePassengerPosts( ): ResultWrapper<List<PassengerPost>>
    suspend fun getHistoryPassengerPosts(
                                      page: Int): ResultWrapper<List<PassengerPost>>
    suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPost>

    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>
    suspend fun getDriverOffers(postId: Long): ResultWrapper<List<OfferDTO>>
    suspend fun getDriverOffersForParcel(postId: Long): ResultWrapper<List<OfferDTO>>

}