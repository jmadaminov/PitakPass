package com.novatec.epitak_passenger.data.source

import com.novatec.epitak_passenger.data.repository.PassengerPostDataStore
import com.novatec.epitak_passenger.data.repository.PassengerPostRemote
import com.novatec.epitak_passenger.data.repository.PlaceDataStore
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.remote.model.OfferDTO
import com.novatec.epitak_passenger.util.ResultWrapper
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PassengerPostRemoteDataStore @Inject constructor(private val passengerPostRemote: PassengerPostRemote) :
    PassengerPostDataStore {

    override suspend fun createPassengerPost(post: PassengerPost) =
        passengerPostRemote.createPost(post)

    override suspend fun deletePassengerPost(identifier: String) =
        passengerPostRemote.deletePost(identifier)

    override suspend fun finishPassengerPost(identifier: String) =
        passengerPostRemote.finishPost(identifier)

    override suspend fun getActivePassengerPosts() = passengerPostRemote.getActivePosts()

    override suspend fun getHistoryPassengerPosts(page: Int) =
        passengerPostRemote.getHistoryPosts(page)

    override suspend fun getPassengerPostById(id: Long) =
        passengerPostRemote.getPassengerPostById(id)

    override suspend fun acceptOffer(id: Long) =
        passengerPostRemote.acceptOffer(id)

    override suspend fun rejectOffer(id: Long) =
        passengerPostRemote.rejectOffer(id)

    override suspend fun cancelMyOffer(id: Long) =
        passengerPostRemote.cancelMyOffer(id)

    override suspend fun getDriverOffers(postId: Long) =
        passengerPostRemote.getDriverOffers(postId)

}