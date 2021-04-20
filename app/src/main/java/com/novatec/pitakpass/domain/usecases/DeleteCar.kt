package com.novatec.pitakpass.domain.usecases
//
//import com.novatec.pitakpass.util.Constants
//import com.novatec.pitakpass.util.ResultWrapper
//import com.novatec.pitakpass.domain.repository.CarRepository
//
//
///** User Login Use Case
// *
// */
//class DeleteCar(val repository: CarRepository) :
//    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {
//
//    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
//        return repository.deleteCar(params[Constants.TXT_TOKEN]!! as String, params[Constants.TXT_ID]!! as Long)
//
//    }
//}