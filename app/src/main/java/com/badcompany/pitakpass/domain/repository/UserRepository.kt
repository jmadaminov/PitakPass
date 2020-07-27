package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.Car
import com.badcompany.pitakpass.domain.model.User
import com.badcompany.pitakpass.domain.model.UserCredentials
import com.badcompany.pitakpass.domain.model.AuthBody

interface UserRepository {

    suspend fun loginUser(phoneNum: String): ResultWrapper<String>
    suspend fun registerUser(user: User): ResultWrapper<String>
    suspend fun smsConfirm(userCredentials: UserCredentials): ResultWrapper<AuthBody>

    fun updateUserDetails(user: User): ResultWrapper<Unit>
    fun addOrUpdateUserCar(car: Car): ResultWrapper< Unit>
    fun getUserCars(userId: String): ResultWrapper< List<Car>>
    fun deleteUserCar(carId: String): ResultWrapper< List<Car>>

}