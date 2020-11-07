package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.data.source.PassengerPostDataStoreFactory
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class PassengerPostRepositoryImpl @Inject constructor(private val factoryPassenger: PassengerPostDataStoreFactory) :
    PassengerPostRepository {


    override suspend fun createPassengerPost(post: PassengerPost) =
        factoryPassenger.retrieveDataStore(false)
            .createPassengerPost(post)

    override suspend fun deletePassengerPost(identifier: String) =
        factoryPassenger.retrieveDataStore(false).deletePassengerPost(identifier)

    override suspend fun finishPassengerPost(identifier: String) =
        factoryPassenger.retrieveDataStore(false).finishPassengerPost(identifier)

    override suspend fun getActivePassengerPosts() =
        factoryPassenger.retrieveDataStore(false).getActivePassengerPosts()

    override suspend fun getHistoryPassengerPosts(page: Int) =
        factoryPassenger.retrieveDataStore(false).getHistoryPassengerPosts(page)

    override suspend fun getPassengerPostById(id: Long) =
        factoryPassenger.retrieveDataStore(false).getPassengerPostById(id)

//    override suspend fun getHistoryPassengerPosts(
//
//                                               page: Int): ResultWrapper<List<PassengerPost>> {
//        val response =
//            factoryPassenger.retrieveDataStore(false).getHistoryPassengerPosts(,page)
//
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                val posts = arrayListOf<PassengerPost>()
//                response.value.forEach { posts.add(driverPostMapper.mapFromEntity(it)) }
//                ResultWrapper.Success(posts)
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }


}