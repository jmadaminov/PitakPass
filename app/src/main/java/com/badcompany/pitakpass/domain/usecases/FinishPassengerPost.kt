package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class FinishPassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<String, ResultWrapper<Unit>>() {

    override suspend fun buildUseCase(params: String) =
        repositoryPassenger.finishPassengerPost(params)
}