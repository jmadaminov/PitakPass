package com.novatec.epitak_passenger.data.repository

import com.novatec.epitak_passenger.domain.model.Car
import com.novatec.epitak_passenger.domain.model.CarColor
import com.novatec.epitak_passenger.domain.model.CarDetails
import com.novatec.epitak_passenger.domain.model.CarModel
import com.novatec.epitak_passenger.util.ResultWrapper


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface CarDataStore {
//    suspend fun getCars(token: String): ResultWrapper<List<CarDetails>>
    suspend fun getCarModels( ): ResultWrapper<List<CarModel>>
    suspend fun getCarColors( ): ResultWrapper<List<CarColor>>
//    suspend fun createCar( car: Car): ResultWrapper<String>
//    suspend fun updateCar( car: Car): ResultWrapper<String>
//    suspend fun deleteCar( id: Long): ResultWrapper<String>
//    suspend fun setDefaultCar( id: Long): ResultWrapper<String>
}