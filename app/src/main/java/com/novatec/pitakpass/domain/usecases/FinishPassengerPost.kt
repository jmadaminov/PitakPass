package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.domain.repository.PassengerPostRepository
import com.novatec.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class FinishPassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<String, ResultWrapper<Unit>>() {

    override suspend fun buildUseCase(params: String) =
        repositoryPassenger.finishPassengerPost(params)
}