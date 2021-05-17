package com.novatec.epitak_passenger.core.enums

enum class EPostStatus {
    WAITING_FOR_START,
    START,
    CANCELED,
    FINISHED,
    REJECTED,
    CREATED,
    SYSTEM_REJECTED;

    fun isOfferable(): Boolean {
        return this == CREATED
    }

}