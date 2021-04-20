package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.CarModel
import com.novatec.pitakpass.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarModels(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarModel>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarModel>> {
        return repository.getCarModels()
    }
}