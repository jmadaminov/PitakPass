package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.domain.model.UserCredentials
import com.novatec.epitak_passenger.domain.repository.UserRepository
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ResponseError
import com.novatec.epitak_passenger.util.ResponseWrapper


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