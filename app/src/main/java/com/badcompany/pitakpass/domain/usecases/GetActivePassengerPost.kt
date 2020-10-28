package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.domain.repository.DriverPostRepository
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository


/** User Login Use Case
 *
 */
class GetActivePassengerPost(val repositoryPassenger: PassengerPostRepository) :
    UseCase<ResultWrapper<List<PassengerPost>>>() {

    override suspend fun buildUseCase(): ResultWrapper<List<PassengerPost>> {
        return repositoryPassenger.getActivePassengerPosts()
    }
}