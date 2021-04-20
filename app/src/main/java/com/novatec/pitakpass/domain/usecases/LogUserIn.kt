package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.domain.model.UserCredentials
import com.novatec.pitakpass.domain.repository.UserRepository
import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ResponseError
import com.novatec.pitakpass.util.ResponseWrapper


/** User Login Use Case
 *
 */
class LogUserIn(val repository: UserRepository) :
    UseCaseWithParams<String, ResponseWrapper<UserCredentials?>>() {

    override suspend fun buildUseCase(params: String): ResponseWrapper<UserCredentials?> {
        if (params.length != 12) {
            return ResponseError(code = Constants.errPhoneFormat)
        }
        return repository.loginUser(params)
    }

}