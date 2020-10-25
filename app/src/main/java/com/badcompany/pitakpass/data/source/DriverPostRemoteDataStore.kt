package com.badcompany.pitakpass.data.source

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.repository.DriverPostDataStore
import com.badcompany.pitakpass.data.repository.DriverPostRemote
import com.badcompany.pitakpass.data.repository.PlaceDataStore
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.DriverPost
import javax.inject.Inject

/**
 * Implementation of the [PlaceDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class DriverPostRemoteDataStore @Inject constructor(private val postRemote: DriverPostRemote) :
    DriverPostDataStore {

    override suspend fun filterDriverPost(token: String,
                                             lang: String,
                                             filter: Filter): ResultWrapper<List<DriverPost>> {

        return postRemote.filterDriverPost(token, lang, filter)
    }

    override suspend fun getPostById(id: Int)= postRemote.getPostById(id)

}