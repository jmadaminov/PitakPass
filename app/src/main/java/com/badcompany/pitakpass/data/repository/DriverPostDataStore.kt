package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.DriverPostEntity
import com.badcompany.pitakpass.domain.domainmodel.DriverPost


interface DriverPostDataStore {
    suspend fun createDriverPost(token: String, post: DriverPostEntity): ResultWrapper<String>
    suspend fun deleteDriverPost(token: String, identifier: String): ResultWrapper<String>
    suspend fun finishDriverPost(token: String, identifier: String): ResultWrapper<String>
    suspend fun getActiveDriverPosts(token: String, lang: String): ResultWrapper<List<DriverPostEntity>>
    suspend fun getHistoryDriverPosts(token: String, lang: String,
                                      page: Int): ResultWrapper<List<DriverPostEntity>>

}