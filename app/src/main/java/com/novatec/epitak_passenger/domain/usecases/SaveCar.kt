package com.novatec.epitak_passenger.domain.usecases
//
//import com.novatec.epitak_passenger.util.Constants
//import com.novatec.epitak_passenger.util.ResultWrapper
//import com.novatec.epitak_passenger.domain.model.Car
//import com.novatec.epitak_passenger.domain.repository.CarRepository
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