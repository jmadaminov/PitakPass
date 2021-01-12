package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class CreatePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<PassengerPost, ResultWrapper<PassengerPost>>() {

    override suspend fun buildUseCase(params: PassengerPost): ResultWrapper<PassengerPost> {
        return repositoryPassenger.createPassengerPost(params)

    }
}