package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.repository.PassengerPostRepository
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ResultWrapper


/** User Login Use Case
 *
 */
class GetHistoryPassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.getHistoryPassengerPosts(params[Constants.TXT_PAGE] as Int)
    }
}