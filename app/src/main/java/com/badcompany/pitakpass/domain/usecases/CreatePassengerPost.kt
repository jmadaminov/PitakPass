package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper


/** User Login Use Case
 *
 */
class CreatePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
        return repositoryPassenger.createPassengerPost(params[Constants.TXT_PASSENGER_POST] as PassengerPost)

    }
}