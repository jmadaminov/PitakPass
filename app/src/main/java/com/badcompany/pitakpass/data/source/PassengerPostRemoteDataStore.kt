package com.badcompany.pitakpass.data.source

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.FilterEntity
import com.badcompany.pitakpass.data.model.PassengerPostEntity
import com.badcompany.pitakpass.data.repository.PassengerPostDataStore
import com.badcompany.pitakpass.data.repository.PassengerPostRemote
import com.badcompany.pitakpass.data.repository.PlaceDataStore
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PassengerPostRemoteDataStore @Inject constructor(private val passengerPostRemote: PassengerPostRemote) :
    PassengerPostDataStore {

    override suspend fun filterPassengerPost(token: String,
                                             lang: String,
                                             filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>> {

        return passengerPostRemote.filterPassengerPost(token, lang, filter)
    }

}