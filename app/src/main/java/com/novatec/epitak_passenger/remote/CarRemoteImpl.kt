package com.novatec.epitak_passenger.remote

import com.novatec.epitak_passenger.data.repository.CarRemote
import com.novatec.epitak_passenger.domain.model.CarColor
import com.novatec.epitak_passenger.domain.model.CarModel
import com.novatec.epitak_passenger.util.ErrorWrapper
import com.novatec.epitak_passenger.util.ResultWrapper
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class CarRemoteImpl @Inject constructor(
    private val apiService: ApiService,
    private val authApi: AuthApi
) :
    CarRemote {

    override suspend fun getCarModels(): ResultWrapper<List<CarModel>> {
        return try {
            val response = authApi.getCarModels()
            if (response.code == 1) {
                val newCarModels = ArrayList<CarModel>()
                response.data!!.forEach {
                    newCarModels.add(it)
                }
                ResultWrapper.Success(newCarModels)
            } else ErrorWrapper.ResponseError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getCarColors(): ResultWrapper<List<CarColor>> {
        return try {
            val response = authApi.getCarColors()
            if (response.code == 1) {
                val newCarColors = ArrayList<CarColor>()
                response.data!!.forEach {
                    newCarColors.add(it)
                }
                ResultWrapper.Success(newCarColors)
            } else ErrorWrapper.ResponseError(response.code, response.message ?: "")
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}