package com.novatec.pitakpass.domain.usecases
//
//import com.novatec.pitakpass.util.ResultWrapper
//import com.novatec.pitakpass.domain.model.CarDetails
//import com.novatec.pitakpass.domain.repository.CarRepository
//
//
///** User Login Use Case
// *
// */
//class GetCars(val repository: CarRepository) :
//    UseCaseWithParams<String, ResultWrapper<List<CarDetails>>>() {
//
//    override suspend fun buildUseCase(params: String): ResultWrapper<List<CarDetails>> {
//        return repository.getCars(params)
//    }
//}