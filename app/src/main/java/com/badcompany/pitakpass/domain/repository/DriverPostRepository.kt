package com.badcompany.pitakpass.domain.repository

import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.ResultWrapper

interface DriverPostRepository {

    suspend fun filterDriverPost(token: String,
                                 lang: String,
                                 filter: Filter): ResultWrapper<List<DriverPost>>
    suspend fun getPostById(id: Int): ResponseWrapper<DriverPost>


}