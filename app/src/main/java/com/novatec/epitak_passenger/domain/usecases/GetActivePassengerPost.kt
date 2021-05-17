package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.repository.DriverPostRepository
import com.novatec.epitak_passenger.domain.repository.PassengerPostRepository


/** User Login Use Case
 *
 */
class GetActivePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCase<ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.getActivePassengerPosts()
    }
}