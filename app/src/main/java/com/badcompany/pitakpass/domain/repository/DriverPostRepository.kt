package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.DriverPost

interface DriverPostRepository {

   suspend fun filterDriverPost(token: String,
                                   lang: String,
                                   filter: Filter): ResultWrapper<List<DriverPost>>

}