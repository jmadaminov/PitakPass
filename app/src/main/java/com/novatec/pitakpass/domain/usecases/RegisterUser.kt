package com.novatec.pitakpass.domain.usecases

import com.novatec.pitakpass.util.Constants
import com.novatec.pitakpass.util.ErrorWrapper
import com.novatec.pitakpass.util.ResultWrapper
import com.novatec.pitakpass.domain.model.User
import com.novatec.pitakpass.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class RegisterUser(val repository: UserRepository) :
    UseCaseWithParams<User, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: User): ResultWrapper<String> {
        if (params.phoneNum.length != 12) return ErrorWrapper.ResponseError(Constants.errPhoneFormat)
        return repository.registerUser(params)
    }

}