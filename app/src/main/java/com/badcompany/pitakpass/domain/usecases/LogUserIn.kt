package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class LogUserIn(val repository: UserRepository) :
    UseCaseWithParams<String, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<String> {
        if (params.length != 12) {
            return ErrorWrapper.ResponseError(Constants.errPhoneFormat)
        }
        return repository.loginUser(params)
    }

}