package com.novatec.pitakpass.domain.repository

import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.util.ResponseWrapper
import com.novatec.pitakpass.util.ResultWrapper

interface PassengerPostRepository {

    suspend fun createPassengerPost(post: PassengerPost): ResultWrapper<PassengerPost>
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