package com.novatec.epitak_passenger.domain.repository

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.*


interface CarRepository {

//    suspend fun getCars(token: String): ResultWrapper<List<CarDetails>>
    suspend fun getCarModels( ): ResultWrapper<List<CarModel>>
    suspend fun getCarColors( ): ResultWrapper<List<CarColor>>
//    suspend fun createCar( car: Car): ResultWrapper<String>
//    suspend fun updateCar( car: Car): ResultWrapper<String>
//    suspend fun deleteCar( id: Long): ResultWrapper<String>
//    suspend fun setDefaultCar( id:Long): ResultWrapper<String>


}