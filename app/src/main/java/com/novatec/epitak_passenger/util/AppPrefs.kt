package com.novatec.epitak_passenger.util

/**
 * Created by jahon on 23-Apr-20
 */


import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi
object AppPrefs : Preferences("appPrefs") {

    var hasSeenTutorial by boolPref("hasSeenTutorial", false)
    var isFirstTime by boolPref("isFirstTime", true)
    var language by StringPref("LANGUAGE", "ru")
}