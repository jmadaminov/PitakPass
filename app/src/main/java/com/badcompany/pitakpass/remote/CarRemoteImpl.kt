package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.data.repository.CarRemote
import com.badcompany.pitakpass.domain.model.CarColor
import com.badcompany.pitakpass.domain.model.CarModel
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import javax.inject.Inject


/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class CarRemoteImpl @Inject constructor(private val apiService: ApiService,
                                        private val authorizedApiService: AuthorizedApiService) :
    CarRemote {

    override suspend fun getCarModels(): ResultWrapper<List<CarModel>> {
        return try {
            val response = authorizedApiService.getCarModels()
            if (response.code == 1) {
                val newCarModels = ArrayList<CarModel>()
                response.data!!.forEach {
                    newCarModels.add(it)
                }
                ResultWrapper.Success(newCarModels)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun getCarColors(): ResultWrapper<List<CarColor>> {
        return try {
            val response = authorizedApiService.getCarColors()
            if (response.code == 1) {
                val newCarColors = ArrayList<CarColor>()
                response.data!!.forEach {
                    newCarColors.add(it)
                }
                ResultWrapper.Success(newCarColors)
            } else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


}