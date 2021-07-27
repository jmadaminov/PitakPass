package com.novatec.epitak_passenger


import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.novatec.epitak_passenger.core.enums.ENotificationType
import com.novatec.epitak_passenger.ui.main.MainActivity
import com.novatec.epitak_passenger.ui.passenger_post.PassengerPostActivity
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import splitties.activities.start

/**
 * Created by jahon on 13-Mar-18.
 */

const val ONESIGNAL_APP_ID = "25ebcc27-1c6c-4a67-a305-10c7c065ef9e"

@HiltAndroidApp
class App : Application() {


    companion object {
        lateinit var uuid: String
        lateinit var INSTANCE: App
        lateinit var versionName: String

        fun getResources() = INSTANCE.resources
        fun getAppContext() = INSTANCE
        fun getInstance(): Context = INSTANCE
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
//        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setNotificationOpenedHandler { result ->
            val actionType = result.action.type
            val data = result.notification.additionalData

            val notificationType = data["notificationType"]
            val postId: Long? = data?.optLong("postId")


            if (postId != null && notificationType == ENotificationType.OFFER_CREATE.name) {
                getInstance()
                    .startActivity(Intent(getInstance(), MainActivity::class.java).apply {
                        putExtra(PassengerPostActivity.EXTRA_POST_ID, postId)
                        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
            } /*else if(postId != null && notificationType == ENotificationType.POST_FINISHED.name) {
            App.getInstance()?.start<MainActivity> {
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        } */ else {
                getInstance().start<MainActivity> {
                    addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }

        }
        OneSignal.getDeviceState()?.userId?.let {
            uuid = it
        } ?: run {
            OneSignal.addSubscriptionObserver { stateChanges ->
                if (!stateChanges!!.from.isSubscribed &&
                    stateChanges.to.isSubscribed
                ) {
                    // get player ID
                    uuid = stateChanges.to.userId
                }
                Log.i("Debug", "onOSPermissionChanged: $stateChanges")
            }
        }

        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        versionName = info.versionName
    }


}


