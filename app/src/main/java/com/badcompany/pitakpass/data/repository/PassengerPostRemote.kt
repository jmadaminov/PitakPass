package com.badcompany.pitakpass.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.remote.model.OfferDTO
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.ResultWrapper

interface PassengerPostRemote {

    suspend fun createPassengerPost(post: PassengerPost): ResultWrapper<PassengerPost>
    suspend fun deletePassengerPost(identifier: String): ResultWrapper<Unit>
    suspend fun finishPassengerPost(identifier: String): ResultWrapper<Unit>
    suspend fun getActivePassengerPosts(): ResultWrapper<List<PassengerPost>>

    suspend fun getHistoryPassengerPosts(page: Int): ResultWrapper<List<PassengerPost>>

    suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPost>

    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>

}