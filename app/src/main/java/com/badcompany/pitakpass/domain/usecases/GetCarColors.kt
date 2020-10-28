package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.CarColor
import com.badcompany.pitakpass.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarColors(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarColor>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarColor>> {
        return repository.getCarColors()
    }
}