package com.novatec.pitakpass.domain.usecases
//
//import com.novatec.pitakpass.util.Constants
//import com.novatec.pitakpass.util.ResultWrapper
//import com.novatec.pitakpass.domain.model.Car
//import com.novatec.pitakpass.domain.repository.CarRepository
//
//
///** User Login Use Case
// *
// */
//class SaveCar(val repository: CarRepository) :
//    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {
//
//    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
//        return if ((params[Constants.TXT_CAR] as Car).id == null)
//            repository.createCar(
//                                 params[Constants.TXT_CAR] as Car)
//        else repository.updateCar(
//                                  params[Constants.TXT_CAR] as Car)
//
//    }
//}