package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.Filter
import com.badcompany.pitakpass.domain.model.PassengerOffer
import com.badcompany.pitakpass.util.ResponseWrapper
import com.badcompany.pitakpass.util.ResultWrapper

interface DriverPostRemote {

    suspend fun filterDriverPost(filter: Filter): ResultWrapper<List<DriverPost>>
    suspend fun getPostById(id: Int): ResponseWrapper<DriverPost>
    suspend fun joinARide(myOffer: PassengerOffer): ResponseWrapper<Any>

}