package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.domain.repository.PassengerPostRepository
import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class GetHistoryPassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.getHistoryPassengerPosts(params[Constants.TXT_PAGE] as Int)
    }
}