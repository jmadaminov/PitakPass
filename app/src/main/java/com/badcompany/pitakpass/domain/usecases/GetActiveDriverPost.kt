package com.badcompany.pitakpass.domain.usecases

import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.DriverPost
import com.badcompany.pitakpass.domain.repository.DriverPostRepository


/** User Login Use Case
 *
 */
class GetActiveDriverPost(val repositoryDriver: DriverPostRepository) :
    UseCaseWithParams<HashMap<String, String>, ResultWrapper<List<DriverPost>>>() {

    override suspend fun buildUseCase(params: HashMap<String, String>): ResultWrapper<List<DriverPost>> {
        return repositoryDriver.getActiveDriverPosts(params[Constants.TXT_TOKEN] as String,
                                                     params[Constants.TXT_LANG] as String)
    }
}