package com.badcompany.pitakpass


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by jahon on 13-Mar-18.
 */

@HiltAndroidApp
class App : Application() {


    companion object {
        lateinit var uuid: String
        private var INSTANCE: App? = null

        fun getResources() = INSTANCE?.resources
        fun getAppContext() = INSTANCE
        fun getInstance(): Context? = INSTANCE
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationOpenedHandler( NotificationOpenHandler())
            .init()


        OneSignal.idsAvailable { userId, registrationId ->
            Log.i("USERRR IDD ONE SIGNAL", "            $userId")
            Log.i("USERRR IDD ONE SIGNAL", "            $userId")
            Log.i("USERRR IDD ONE SIGNAL", "            $userId")
            uuid = userId
        }


    }


}


