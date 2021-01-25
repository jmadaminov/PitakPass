package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.PassengerOffer
import com.badcompany.pitakpass.remote.model.DriverPostsPagination
import com.badcompany.pitakpass.util.ResponseWrapper

interface DriverPostRemote {

    suspend fun filterDriverPost(filter: Filter): ResponseWrapper<DriverPostsPagination>
    suspend fun getPostById(id: Long): ResponseWrapper<DriverPost>
    suspend fun joinARide(myOffer: PassengerOffer): ResponseWrapper<Any>

}