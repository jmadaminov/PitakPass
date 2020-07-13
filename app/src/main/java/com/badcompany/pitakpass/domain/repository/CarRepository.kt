package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.*


interface CarRepository {

    suspend fun getCars(token: String): ResultWrapper<List<CarDetails>>
    suspend fun getCarModels(token: String, lang:String): ResultWrapper<List<CarModelBody>>
    suspend fun getCarColors(token: String, lang:String): ResultWrapper<List<CarColorBody>>
    suspend fun createCar(token: String, car: Car): ResultWrapper<String>
    suspend fun updateCar(token: String, car: Car): ResultWrapper<String>
    suspend fun deleteCar(token: String, id: Long): ResultWrapper<String>
    suspend fun setDefaultCar(token: String, id:Long): ResultWrapper<String>


}