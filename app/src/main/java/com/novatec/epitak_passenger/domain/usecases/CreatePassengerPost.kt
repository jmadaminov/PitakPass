package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.repository.PassengerPostRepository
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ResultWrapper


/** User Login Use Case
 *
 */
class CreatePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<PassengerPost, ResultWrapper<PassengerPost?>>() {

    override suspend fun buildUseCase(params: PassengerPost): ResultWrapper<PassengerPost?> {
        return repositoryPassenger.createPassengerPost(params)

    }
}