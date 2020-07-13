package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.Car
import com.badcompany.pitakpass.domain.repository.CarRepository


/** User Login Use Case
 *
 */
class SaveCar(val repository: CarRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
        return if ((params[Constants.TXT_CAR] as Car).id == null)
            repository.createCar(params[Constants.TXT_TOKEN] as String,
                                 params[Constants.TXT_CAR] as Car)
        else repository.updateCar(params[Constants.TXT_TOKEN] as String,
                                  params[Constants.TXT_CAR] as Car)

    }
}