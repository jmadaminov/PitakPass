package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.User
import com.badcompany.pitakpass.domain.repository.UserRepository


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