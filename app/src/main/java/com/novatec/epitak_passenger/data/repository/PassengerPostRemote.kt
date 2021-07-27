package com.novatec.epitak_passenger.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.remote.model.OfferDTO
import com.novatec.epitak_passenger.util.ResponseWrapper
import com.novatec.epitak_passenger.util.ResultWrapper

interface PassengerPostRemote {

    suspend fun createPost(post: PassengerPost): ResultWrapper<PassengerPost?>
    suspend fun deletePost(identifier: String): ResultWrapper<Unit>
    suspend fun finishPost(identifier: String): ResultWrapper<Unit>
    suspend fun getActivePosts(): ResultWrapper<List<PassengerPost>>

    suspend fun getHistoryPosts(page: Int): ResultWrapper<List<PassengerPost>>

    suspend fun getPassengerPostById(id: Long): ResponseWrapper<PassengerPost>

    suspend fun acceptOffer(id: Long): ResponseWrapper<String?>
    suspend fun rejectOffer(id: Long): ResponseWrapper<String?>
    suspend fun cancelMyOffer(id: Long): ResponseWrapper<String?>
    suspend fun getDriverOffers(postId: Long): ResultWrapper<List<OfferDTO>>


}