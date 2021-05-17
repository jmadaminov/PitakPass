package com.novatec.epitak_passenger.remote.model

import com.novatec.epitak_passenger.domain.model.PassengerPost

/**
 * Created by jahon on 12-Apr-20
 */
data class PassengerActivePostsResponse(val code: Int? = null,
                                        val message: String? = null,
                                        val data: List<PassengerPost>? = null)

