//package com.novatec.pitakpass.domain.usecases
//
//import com.novatec.pitakpass.domain.model.DriverPost
//import com.novatec.pitakpass.domain.model.Filter
//import com.novatec.pitakpass.domain.repository.DriverPostRepository
//import com.novatec.pitakpass.util.Constants
//import com.novatec.pitakpass.util.ResultWrapper
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