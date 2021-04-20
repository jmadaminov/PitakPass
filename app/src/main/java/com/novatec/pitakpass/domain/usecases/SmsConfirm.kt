package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.AuthBody
import com.novatec.pitakpass.domain.model.UserCredentials
import com.novatec.pitakpass.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class SmsConfirm(val repository: UserRepository) :
    UseCaseWithParams<UserCredentials, ResultWrapper<AuthBody>>() {

    override suspend fun buildUseCase(params: UserCredentials): ResultWrapper<AuthBody> {
        return repository.smsConfirm(params)
    }

}