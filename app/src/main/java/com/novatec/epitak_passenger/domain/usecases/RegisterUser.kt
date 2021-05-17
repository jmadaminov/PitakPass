package com.novatec.epitak_passenger.domain.usecases

import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.ErrorWrapper
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.domain.repository.UserRepository


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