package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.DriverPost
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.domain.repository.DriverPostRepository
import com.novatec.pitakpass.domain.repository.PassengerPostRepository


/** User Login Use Case
 *
 */
class GetActivePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCase<ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.getActivePassengerPosts()
    }
}