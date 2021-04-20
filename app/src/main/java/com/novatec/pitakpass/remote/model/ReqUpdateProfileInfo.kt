package com.novatec.pitakpass.remote.model

import com.novatec.pitakpass.domain.model.IdName

data class ReqUpdateProfileInfo(
                                val name: String,
                                val surname: String,
                                val image: IdName? = null)