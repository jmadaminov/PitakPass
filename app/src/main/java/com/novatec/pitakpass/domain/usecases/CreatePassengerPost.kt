package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.domain.repository.PassengerPostRepository
import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class CreatePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<PassengerPost, ResultWrapper<PassengerPost?>>() {

    override suspend fun buildUseCase(params: PassengerPost): ResultWrapper<PassengerPost?> {
        return repositoryPassenger.createPassengerPost(params)

    }
}