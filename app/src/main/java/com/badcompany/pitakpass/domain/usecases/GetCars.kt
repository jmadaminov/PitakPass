package com.badcompany.pitakpass.domain.usecases
//
//import com.badcompany.pitakpass.util.ResultWrapper
//import com.badcompany.pitakpass.domain.model.CarDetails
//import com.badcompany.pitakpass.domain.repository.CarRepository
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