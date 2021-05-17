package com.novatec.epitak_passenger.remote

import com.novatec.epitak_passenger.data.repository.UserRemote
import com.novatec.epitak_passenger.domain.model.*
import com.novatec.epitak_passenger.remote.model.LoginRequest
import com.novatec.epitak_passenger.remote.model.ReqUpdateProfileInfo
import com.novatec.epitak_passenger.util.*
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl @Inject constructor(private val apiService: ApiService,
                                         private val authorizedApiService: AuthorizedApiService) :
    UserRemote {


    override suspend fun loginUser(phoneNum: String) =
        getFormattedResponseNullable { apiService.userLogin(LoginRequest(phoneNum)) }


    override suspend fun registerUser(user: User): ResultWrapper<String> {
        return try {
            val response = apiService.userRegister(user)
            if (response.code == 1) ResultWrapper.Success("")
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

    override suspend fun updateUserInfo(name: String,
                                        surName: String,
                                        uploadedAvatarId: Long?) =
        getFormattedResponseNullable {
            authorizedApiService.updateUserInfo(ReqUpdateProfileInfo(name,
                                                                     surName,
                                                                     uploadedAvatarId?.let {
                                                                         IdName(uploadedAvatarId)
                                                                     }))
        }

    override suspend fun getActiveAppVersions(): ResponseWrapper<List<String>> {
        val response = getFormattedResponse { apiService.getActiveAppVersions() }
        return when (response) {
            is ResponseError -> response
            is ResponseSuccess -> {
                val versions = arrayListOf<String>()
                response.value.forEach {
                    versions.add(it.version)
                }
                ResponseSuccess(versions)
            }
        }.exhaustive
    }

}