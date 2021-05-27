package com.novatec.epitak_passenger.core.enums

enum class EPostStatus {
    WAITING_FOR_START,
    START,
    CANCELED,
    FINISHED,
    REJECTED,
    CREATED,
    SYSTEM_REJECTED;

    fun isOfferableForPassenger(): Boolean {
        return this == CREATED
    }

    fun isOfferableForParcel(): Boolean {
        return this == CREATED || this == WAITING_FOR_START
    }

}