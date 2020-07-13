package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.Filter
import com.badcompany.pitakpass.domain.domainmodel.PassengerPost
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository

/** User Login Use Case
 *
 */
class GetPassengerPostWithFilter(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.filterPassengerPost(params[Constants.TXT_TOKEN] as String,
                                                       params[Constants.TXT_LANG] as String,
                                                       params[Constants.TXT_FILTER] as Filter)
    }
}