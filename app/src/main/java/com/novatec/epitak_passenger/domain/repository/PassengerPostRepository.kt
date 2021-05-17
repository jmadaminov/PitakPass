package com.novatec.epitak_passenger.domain.repository

import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.util.ResponseWrapper
import com.novatec.epitak_passenger.util.ResultWrapper

interface PassengerPostRepository {

    suspend fun createPassengerPost(post: PassengerPost): ResultWrapper<PassengerPost?>
    suspend fun deletePassengerPost(identifier: String): ResultWrapper<Unit>
    suspend fun finishPassengerPost(identifier: String): ResultWrapper<Unit>
    suspend fun getActivePassengerPosts(): ResultWrapper<List<PassengerPost>>
    suspend fun getHistoryPassengerPosts(
        page: Int): ResultWrapper<List<PassengerPost>>

    suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPost>

    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>

}