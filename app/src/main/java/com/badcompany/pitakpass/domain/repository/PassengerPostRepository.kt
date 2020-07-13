package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.Filter
import com.badcompany.pitakpass.domain.domainmodel.PassengerPost

interface PassengerPostRepository {

    suspend fun filterPassengerPost(token: String,
                                    lang: String,
                                    filter: Filter): ResultWrapper<List<PassengerPost>>
}