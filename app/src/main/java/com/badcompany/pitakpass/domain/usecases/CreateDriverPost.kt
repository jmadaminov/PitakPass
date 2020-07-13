package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.DriverPost
import com.badcompany.pitakpass.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class CreateDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<HashMap<String, Any>, ResultWrapper<String>>() {

    override suspend fun buildUseCase(params: HashMap<String, Any>): ResultWrapper<String> {
        return repositoryDriver.createDriverPost(params[Constants.TXT_TOKEN] as String,
                                                 params[Constants.TXT_DRIVER_POST] as DriverPost)

    }
}