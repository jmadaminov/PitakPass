package com.badcompany.pitakpass.data.source

import com.badcompany.pitakpass.data.repository.PassengerPostDataStore
import com.badcompany.pitakpass.data.repository.PassengerPostRemote
import com.badcompany.pitakpass.data.repository.PlaceDataStore
import com.badcompany.pitakpass.domain.model.PassengerPost
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PassengerPostRemoteDataStore @Inject constructor(private val passengerPostRemote: PassengerPostRemote) :
    PassengerPostDataStore {

    override suspend fun createPassengerPost(post: PassengerPost) =
        passengerPostRemote.createPassengerPost(post)

    override suspend fun deletePassengerPost(identifier: String) =
        passengerPostRemote.deletePassengerPost(identifier)

    override suspend fun finishPassengerPost(identifier: String) =
        passengerPostRemote.finishPassengerPost(identifier)

    override suspend fun getActivePassengerPosts() = passengerPostRemote.getActivePassengerPosts()

    override suspend fun getHistoryPassengerPosts(page: Int) =
        passengerPostRemote.getHistoryPassengerPosts(page)

    override suspend fun getPassengerPostById(id: Long) =
        passengerPostRemote.getPassengerPostById(id)
}