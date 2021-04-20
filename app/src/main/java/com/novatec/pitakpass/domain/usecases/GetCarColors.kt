package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.CarColor
import com.novatec.pitakpass.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarColors(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarColor>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarColor>> {
        return repository.getCarColors()
    }
}