package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.AuthBody
import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class SmsConfirm(val repository: UserRepository) :
    UseCaseWithParams<UserCredentials, ResultWrapper<AuthBody>>() {

    override suspend fun buildUseCase(params: UserCredentials): ResultWrapper<AuthBody> {
        return repository.smsConfirm(params)
    }

}