package com.badcompany.pitakpass.data.repository

import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.data.model.FilterEntity
import com.badcompany.pitakpass.data.model.PassengerPostEntity


interface PassengerPostDataStore {

    suspend fun filterPassengerPost(token: String,
                                    lang: String,
                                    filter: FilterEntity): ResultWrapper<List<PassengerPostEntity>>

}