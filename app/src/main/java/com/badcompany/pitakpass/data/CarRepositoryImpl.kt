package com.badcompany.pitakpass.data

import com.badcompany.pitakpass.data.source.CarDataStoreFactory
import com.badcompany.pitakpass.domain.model.Car
import com.badcompany.pitakpass.domain.model.CarColor
import com.badcompany.pitakpass.domain.model.CarDetails
import com.badcompany.pitakpass.domain.model.CarModel
import com.badcompany.pitakpass.domain.repository.CarRepository
import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import javax.inject.Inject

/**
 * Provides an implementation of the [UserRepository] interface for communicating to and from
 * data sources
 */
class CarRepositoryImpl @Inject constructor(private val factory: CarDataStoreFactory) :
    CarRepository {

//    override suspend fun getCars(token: String): ResultWrapper<List<CarDetails>> {
//        val response = factory.retrieveDataStore(false)
//            .getCars(token)
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                ResultWrapper.Success(response.value)
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }


    override suspend fun getCarModels( ): ResultWrapper<List<CarModel>> {
        val response = factory.retrieveDataStore(false)
            .getCarModels()
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

    override suspend fun getCarColors( ): ResultWrapper<List<CarColor>> {
        val response = factory.retrieveDataStore(false).getCarColors()
        return when (response) {
            is ErrorWrapper.ResponseError -> response
            is ErrorWrapper.SystemError -> response
            is ResultWrapper.Success -> {
                ResultWrapper.Success(response.value)
            }
            ResultWrapper.InProgress -> ResultWrapper.InProgress
        }
    }

//    override suspend fun createCar( car: Car): ResultWrapper<String> {
//        val response = factory.retrieveDataStore(false).createCar( car)
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                ResultWrapper.Success("SUCCESS")
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }
//
//    override suspend fun updateCar( car: Car): ResultWrapper<String> {
//        val response = factory.retrieveDataStore(false).updateCar( car)
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                ResultWrapper.Success("SUCCESS")
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }
//
//    override suspend fun deleteCar( id: Long): ResultWrapper<String> {
//        val response = factory.retrieveDataStore(false)
//            .deleteCar( id)
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                ResultWrapper.Success("SUCCESS")
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }
//
//    override suspend fun setDefaultCar( id: Long): ResultWrapper<String> {
//        val response = factory.retrieveDataStore(false)
//            .setDefaultCar( id)
//        return when (response) {
//            is ErrorWrapper.ResponseError -> response
//            is ErrorWrapper.SystemError -> response
//            is ResultWrapper.Success -> {
//                ResultWrapper.Success("SUCCESS")
//            }
//            ResultWrapper.InProgress -> ResultWrapper.InProgress
//        }
//    }


}