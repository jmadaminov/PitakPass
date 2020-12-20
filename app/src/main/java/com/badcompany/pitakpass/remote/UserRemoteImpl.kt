package com.badcompany.pitakpass.remote

import com.badcompany.pitakpass.data.repository.UserRemote
import com.badcompany.pitakpass.domain.model.AuthBody
import com.badcompany.pitakpass.domain.model.FeedbackBody
import com.badcompany.pitakpass.domain.model.User
import com.badcompany.pitakpass.domain.model.UserCredentials
import com.badcompany.pitakpass.remote.model.LoginRequest
import com.badcompany.pitakpass.util.*
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl @Inject constructor(private val apiService: ApiService,
                                         private val authorizedApiService: AuthorizedApiService) :
    UserRemote {

//    /**
//     * Retrieve a list of [Bufferoo] instances from the [BufferooService].
//     */
//    override fun getBufferoos(): Flowable<List<User>> {
//        return bufferooService.getBufferoos()
//                .map { it.team }
//                .map {
//                    val entities = mutableListOf<User>()
//                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
//                    entities
//                }
//    }

    override suspend fun loginUser(phoneNum: String) =
        getFormattedResponseNullable { apiService.userLogin(LoginRequest(phoneNum)) }


    override suspend fun registerUser(user: User): ResultWrapper<String> {
        return try {
            val response = apiService.userRegister(user)
            if (response.code == 1) ResultWrapper.Success(response.data!!.password!!)
            else ErrorWrapper.ResponseError(response.code, response.message)

        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun confirmUser(user: UserCredentials): ResultWrapper<AuthBody> {
        return try {
            val response = apiService.smsConfirm(user)
            if (response.code == 1) ResultWrapper.Success(response.data!!)
            else ErrorWrapper.ResponseError(response.code, response.message)
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }

    override suspend fun sendFeedback(feedback: String) =
        getFormattedResponseNullable { authorizedApiService.sendFeedback(FeedbackBody(feedback)) }

}