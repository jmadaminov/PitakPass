package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.mapper.FilterMapper
import com.badcompany.pitakpass.data.mapper.PassengerPostMapper
import com.badcompany.pitakpass.data.source.PassengerPostDataStoreFactory
import com.badcompany.pitakpass.domain.domainmodel.Filter
import com.badcompany.pitakpass.domain.domainmodel.PassengerPost
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Provides an implementation of the [PlaceRepository] interface for communicating to and from
 * data sources
 */
class PassengerPostRepositoryImpl @Inject constructor(private val factoryPassenger: PassengerPostDataStoreFactory,
                                                      private val passengerPostMapper: PassengerPostMapper,
                                                      private val filterMapper: FilterMapper) :
    PassengerPostRepository {

    override suspend fun filterPassengerPost(token: String,
                                             lang: String,
                                             filter: Filter): ResultWrapper<List<PassengerPost>> {

        val response = factoryPassenger.retrieveDataStore(false)
            .filterPassengerPost(token, lang, filterMapper.mapToEntity(filter))

        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                val posts = arrayListOf<PassengerPost>()
                response.value.forEach { posts.add(passengerPostMapper.mapFromEntity(it)) }
                ResultWrapper.Success(posts)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }


//    override suspend fun getHistoryPassengerPosts(token: String,
//                                               lang: String,
//                                               page: Int): ResultWrapper<List<PassengerPost>> {
//        val response =
//            factoryPassenger.retrieveDataStore(false).getHistoryPassengerPosts(token, lang,page)
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