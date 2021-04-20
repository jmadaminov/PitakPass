package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.domain.repository.PassengerPostRepository
import com.novatec.pitakpass.util.ErrorWrapper
import com.novatec.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class DeletePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<String, ResultWrapper<Unit>>() {

    override suspend fun buildUseCase(params: String) = repositoryPassenger.deletePassengerPost(params)
}