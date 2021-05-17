package com.novatec.epitak_passenger.remote.model

import com.novatec.epitak_passenger.domain.model.IdName

data class ReqUpdateProfileInfo(
                                val name: String,
                                val surname: String,
                                val image: IdName? = null)