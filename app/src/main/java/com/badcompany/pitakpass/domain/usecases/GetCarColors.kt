package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.CarColorBody
import com.badcompany.pitakpass.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class GetCarColors(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarColorBody>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarColorBody>> {
        return repository.getCarColors(params[Constants.TXT_TOKEN]!!, params[Constants.TXT_LANG]!!)
    }
}