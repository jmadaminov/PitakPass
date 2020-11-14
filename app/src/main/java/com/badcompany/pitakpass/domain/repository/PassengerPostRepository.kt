package com.badcompany.pitakpass.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.remote.model.OfferDTO
import com.badcompany.pitakpass.util.ResponseWrapper

interface PassengerPostRepository {

    suspend fun createPassengerPost( post: PassengerPost): ResultWrapper<String>
    suspend fun deletePassengerPost( identifier: String): ResultWrapper<Unit>
    suspend fun finishPassengerPost( identifier: String): ResultWrapper<Unit>
    suspend fun getActivePassengerPosts( ): ResultWrapper<List<PassengerPost>>
    suspend fun getHistoryPassengerPosts(
                                      page: Int): ResultWrapper<List<PassengerPost>>
    suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPost>


}