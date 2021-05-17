//package com.novatec.epitak_passenger.domain.usecases
//
//import com.novatec.epitak_passenger.domain.model.DriverPost
//import com.novatec.epitak_passenger.domain.model.Filter
//import com.novatec.epitak_passenger.domain.repository.DriverPostRepository
//import com.novatec.epitak_passenger.util.Constants
//import com.novatec.epitak_passenger.util.ResultWrapper
//
///** User Login Use Case
// *
// */
//class GetDriverPostWithFilter(val repositoryDriver: DriverPostRepository) :
//    UseCaseWithParams<Filter, ResultWrapper<List<DriverPost>>>() {
//
//    override suspend fun buildUseCase(params: Filter): ResultWrapper<List<DriverPost>> {
//        return repositoryDriver.filterDriverPost(params)
//    }
//}