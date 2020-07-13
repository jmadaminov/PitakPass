package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.domain.domainmodel.DriverPost
import com.badcompany.pitakpass.domain.domainmodel.PassengerPost

interface DriverPostRepository {

   suspend fun createDriverPost(token: String, post: DriverPost): ResultWrapper<String>
   suspend fun deleteDriverPost(token: String, identifier: String): ResultWrapper<String>
   suspend fun finishDriverPost(token: String, identifier: String): ResultWrapper<String>
   suspend fun getActiveDriverPosts(token: String, lang: String): ResultWrapper<List<DriverPost>>
   suspend fun getHistoryDriverPosts(token: String, lang: String,
                                     page: Int): ResultWrapper<List<DriverPost>>

}