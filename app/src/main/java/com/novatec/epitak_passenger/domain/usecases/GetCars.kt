package com.novatec.epitak_passenger.domain.usecases
//
//import com.novatec.epitak_passenger.util.ResultWrapper
//import com.novatec.epitak_passenger.domain.model.CarDetails
//import com.novatec.epitak_passenger.domain.repository.CarRepository
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