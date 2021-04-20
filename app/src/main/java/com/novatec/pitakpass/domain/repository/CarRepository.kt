package com.novatec.pitakpass.domain.repository

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.*


interface CarRepository {

//    suspend fun getCars(token: String): ResultWrapper<List<CarDetails>>
    suspend fun getCarModels( ): ResultWrapper<List<CarModel>>
    suspend fun getCarColors( ): ResultWrapper<List<CarColor>>
//    suspend fun createCar( car: Car): ResultWrapper<String>
//    suspend fun updateCar( car: Car): ResultWrapper<String>
//    suspend fun deleteCar( id: Long): ResultWrapper<String>
//    suspend fun setDefaultCar( id:Long): ResultWrapper<String>


}