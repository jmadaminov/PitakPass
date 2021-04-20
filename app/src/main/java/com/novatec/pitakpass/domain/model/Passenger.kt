package com.novatec.pitakpass.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [Passenger] fetched from the API
 */
data class Passenger(@SerializedName("id") var id: Long? = null,
                     @SerializedName("profile") var profile: ProfileDTO? = null,
                     @SerializedName("submitDate") var submitDate: String? = null,
                     @SerializedName("offer") val offer: ArrangedOfferDTO? = null)

data class ArrangedOfferDTO(@SerializedName("message") var message: String? = null,
                            @SerializedName("priceInt") var priceInt: Int? = null,
                            @SerializedName("seat") var seat: Int? = null,
                            @SerializedName("history") var history: String? = null)