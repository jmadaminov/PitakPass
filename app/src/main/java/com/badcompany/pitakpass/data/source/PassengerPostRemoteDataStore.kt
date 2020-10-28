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
open class PassengerPostRemoteDataStore @Inject constructor(private val driverPostRemote: PassengerPostRemote) :
    PassengerPostDataStore {

    override suspend fun createPassengerPost(post: PassengerPost) =
        driverPostRemote.createPassengerPost(post)

    override suspend fun deletePassengerPost(identifier: String) =
        driverPostRemote.deletePassengerPost(identifier)

    override suspend fun finishPassengerPost(identifier: String) =
        driverPostRemote.finishPassengerPost(identifier)

    override suspend fun getActivePassengerPosts() = driverPostRemote.getActivePassengerPosts()

    override suspend fun getHistoryPassengerPosts(page: Int) =
        driverPostRemote.getHistoryPassengerPosts(page)


}