package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.domain.model.UserCredentials
import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResponseError
import com.badcompany.pitakpass.util.ResponseWrapper


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