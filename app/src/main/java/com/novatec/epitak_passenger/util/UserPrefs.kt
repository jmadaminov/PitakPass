package com.novatec.epitak_passenger.util

/**
 * Created by jahon on 23-Apr-20
 */


import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi object UserPrefs : Preferences("myPrefs") {

    var userId by StringPref("USER_ID", "")
    var token by StringPref("TOKEN", "")
    var name by StringPref("NAME", "")
    var surname by StringPref("SURNAME", "")
    var phone by StringPref("PHONE", "")
    var avatar by StringPref("AVATAR", "")
}