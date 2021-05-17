package com.novatec.epitak_passenger.util

import com.novatec.epitak_passenger.util.AutoCompleteManager

/**
 * Created by jahon on 22-Jul-20
 */
fun buildAutoCompleteManager(lambda: AutoCompleteManager.Builder.() -> Unit) =
    AutoCompleteManager.Builder().apply(lambda).create()