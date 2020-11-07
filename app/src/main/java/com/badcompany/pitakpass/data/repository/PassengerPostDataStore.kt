package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.util.ResponseWrapper


interface PassengerPostDataStore {

    suspend fun createPassengerPost( post: PassengerPost): ResultWrapper<String>
    suspend fun deletePassengerPost( identifier: String): ResultWrapper<Unit>
    suspend fun finishPassengerPost( identifier: String): ResultWrapper<Unit>
    suspend fun getActivePassengerPosts( ): ResultWrapper<List<PassengerPost>>
    suspend fun getHistoryPassengerPosts(
                                      page: Int): ResultWrapper<List<PassengerPost>>
    suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPost>

}