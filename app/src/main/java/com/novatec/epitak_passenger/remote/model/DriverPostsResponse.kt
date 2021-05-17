package com.novatec.epitak_passenger.remote.model

import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.domain.model.PassengerPost

/**
 * Created by jahon on 12-Apr-20
 */
data class DriverPostsResponse(val code: Int? = null,
                               val message: String? = null,
                               val data: DriverPostsPagination? = null)

data class DriverPostsPagination(val pages: Int? = null,
                                 val elements: Int? = null,
                                 val data: List<DriverPost>? = null)