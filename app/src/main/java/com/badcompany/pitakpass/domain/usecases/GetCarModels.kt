package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.CarModelBody
import com.badcompany.pitakpass.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarModels(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarModelBody>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarModelBody>> {
        return repository.getCarModels(params[Constants.TXT_TOKEN]!!, params[Constants.TXT_LANG]!!)
    }
}