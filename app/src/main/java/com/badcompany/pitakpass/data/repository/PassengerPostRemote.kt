package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.util.ResultWrapper

interface PassengerPostRemote {

    suspend fun createPassengerPost( post: PassengerPost): ResultWrapper<String>
    suspend fun deletePassengerPost( identifier: String): ResultWrapper<String>
    suspend fun finishPassengerPost( identifier: String): ResultWrapper<String>
    suspend fun getActivePassengerPosts(                                     ): ResultWrapper<List<PassengerPost>>

    suspend fun getHistoryPassengerPosts(                                      page: Int): ResultWrapper<List<PassengerPost>>



}