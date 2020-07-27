package com.badcompany.pitakpass.remote.model

import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.domain.model.PassengerPost

/**
 * Created by jahon on 12-Apr-20
 */
data class DriverPostsResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: DriverPostsPagination? = null)

data class DriverPostsPagination(val pages: Int? = null,
                                 val elements: Int? = null,
                                 val data: List<DriverPost>? = null)