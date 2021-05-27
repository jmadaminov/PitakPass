//package com.novatec.epitak_passenger.domain.usecases
//
//import com.novatec.epitak_passenger.util.Constants
//import com.novatec.epitak_passenger.util.ResultWrapper
//import com.novatec.epitak_passenger.domain.model.CarModel
//import com.novatec.epitak_passenger.domain.repository.CarRepository
//
//
///** User Login Use Case
// *
// */
//class GetCarModels(val repository: CarRepository) :
//    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<CarModel>>>() {
//
//    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<CarModel>> {
//        return repository.getCarModels()
//    }
//}