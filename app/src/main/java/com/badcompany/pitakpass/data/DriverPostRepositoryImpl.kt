package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.mapper.DriverPostMapper
import com.badcompany.pitakpass.data.source.DriverPostDataStoreFactory
import com.badcompany.pitakpass.domain.domainmodel.DriverPost
import com.badcompany.pitakpass.domain.repository.DriverPostRepository
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class DriverPostRepositoryImpl @Inject constructor(private val factoryDriver: DriverPostDataStoreFactory,
                                                   private val driverPostMapper: DriverPostMapper) :
    DriverPostRepository {


    override suspend fun createDriverPost(token: String, post: DriverPost): ResultWrapper<String> {
        return factoryDriver.retrieveDataStore(false)
            .createDriverPost(token, driverPostMapper.mapToEntity(post))
    }

    override suspend fun deleteDriverPost(token: String,
                                          identifier: String): ResultWrapper<String> {

        return factoryDriver.retrieveDataStore(false).deleteDriverPost(token, identifier)
    }

    override suspend fun finishDriverPost(token: String,
                                          identifier: String): ResultWrapper<String> {
        return factoryDriver.retrieveDataStore(false).finishDriverPost(token, identifier)

    }

    override suspend fun getActiveDriverPosts(token: String,
                                              lang: String): ResultWrapper<List<DriverPost>> {
        val response =
            factoryDriver.retrieveDataStore(false).getActiveDriverPosts(token, lang)

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {

                val posts = arrayListOf<DriverPost>()
                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getHistoryDriverPosts(token: String,
                                               lang: String,
                                               page: Int): ResultWrapper<List<DriverPost>> {
        val response =
            factoryDriver.retrieveDataStore(false).getHistoryDriverPosts(token, lang,page)

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val posts = arrayListOf<DriverPost>()
                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


}