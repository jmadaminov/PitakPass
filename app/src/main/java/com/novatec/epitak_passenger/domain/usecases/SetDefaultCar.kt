package com.novatec.epitak_passenger.domain.usecases
//
//import com.novatec.epitak_passenger.util.Constants
//import com.novatec.epitak_passenger.util.ResultWrapper
//import com.novatec.epitak_passenger.domain.repository.CarRepository
//
//
///** User Login Use Case
// *
// */
//class SetDefaultCar(val repository: CarRepository) :
//    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {
//
//    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
//        return repository.setDefaultCar( params[Constants.TXT_ID] as Long )
//    }
//}