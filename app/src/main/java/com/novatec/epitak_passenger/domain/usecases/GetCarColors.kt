//package com.novatec.epitak_passenger.domain.usecases
//
//import com.novatec.epitak_passenger.util.Constants
//import com.novatec.epitak_passenger.util.ResultWrapper
//import com.novatec.epitak_passenger.domain.model.CarColor
//import com.novatec.epitak_passenger.domain.repository.CarRepository
//
//
///** User Login Use Case
// *
// */
//class GetCarColors(val repository: CarRepository) :
//    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarColor>>>() {
//
//    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarColor>> {
//        return repository.getCarColors()
//    }
//}