//package com.badcompany.pitakpass.domain.usecases
//
//import com.badcompany.pitakpass.domain.model.DriverPost
//import com.badcompany.pitakpass.domain.model.Filter
//import com.badcompany.pitakpass.domain.repository.DriverPostRepository
//import com.badcompany.pitakpass.util.Constants
//import com.badcompany.pitakpass.util.ResultWrapper
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