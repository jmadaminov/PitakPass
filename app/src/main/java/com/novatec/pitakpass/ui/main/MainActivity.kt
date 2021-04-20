package com.novatec.pitakpass.ui.main

import android.animation.LayoutTransition
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.novatec.pitakpass.R
import com.novatec.pitakpass.ui.BaseActivity
import com.novatec.pitakpass.ui.addpost.AddPostActivity
import com.novatec.pitakpass.ui.auth.AuthActivity
import com.novatec.pitakpass.ui.main.mytrips.MyTripsFragment
import com.novatec.pitakpass.ui.main.notifications.NotificationsFragment
import com.novatec.pitakpass.ui.main.profile.ProfileFragment
import com.novatec.pitakpass.ui.main.searchtrip.SearchTripFragment
import com.novatec.pitakpass.ui.passenger_post.PassengerPostActivity
import com.novatec.pitakpass.ui.passenger_post.PassengerPostActivity.Companion.EXTRA_POST_ID
import com.novatec.pitakpass.util.AppPrefs
import com.novatec.pitakpass.util.AppSignatureHelper
import com.novatec.pitakpass.util.ContextUtils.setLocale
import kotlinx.android.synthetic.main.activity_main.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi

class MainActivity : BaseActivity() {

    companion object {
        private val REQ_CODE_ADD_POST = 98
    }


    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    private var notificationPostId: Long? = null

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(AppPrefs.language, this)
        checkUserLogin()
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)

//        GZQa4cPru4n  DEBUG
//        DqdXBBbaxnT  RELEASE

//        Toast.makeText(this, AppSignatureHelper(this).appSignatures[0]!!, Toast.LENGTH_LONG).show()

        notificationPostId = intent.extras?.getLong(EXTRA_POST_ID, -1)

        if (notificationPostId != null && notificationPostId != -1L) {
            startActivity(Intent(this, PassengerPostActivity::class.java).apply {
                putExtra(EXTRA_POST_ID, notificationPostId)
            })
        }

        setContentView(R.layout.activity_main)
        app_bar.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        setupActionBar()
        setupListeners()
        subscribeObservers()

        navController = findNavController(R.id.nav_host_fragment)
        viewModel.getActiveAppVersions()
    }

    private fun setupListeners() {
        addPost.setOnClickListener {
            startActivityForResult(Intent(this, AddPostActivity::class.java), REQ_CODE_ADD_POST)
        }

        navSearch.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == ProfileFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_profile_to_nav_menu_search)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == MyTripsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_my_trips_to_nav_menu_search)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == NotificationsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_notifications_to_nav_menu_search)
            }
            uncheckAllButMe(navSearch)
        }

        navMyTrips.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == SearchTripFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_search_to_nav_menu_my_trips)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == ProfileFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_profile_to_nav_menu_my_trips)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == NotificationsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_notifications_to_nav_menu_my_trips)
            }
            uncheckAllButMe(navMyTrips)
        }
        navProfile.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == SearchTripFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_search_to_nav_menu_profile)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == MyTripsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_my_trips_to_nav_menu_profile)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == NotificationsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_notifications_to_nav_menu_profile)
            }
            uncheckAllButMe(navProfile)
        }
        navNotifications.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == SearchTripFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_search_to_nav_menu_notifications)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == MyTripsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_my_trips_to_nav_menu_notifications)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == ProfileFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_profile_to_nav_menu_notifications)
            }
            uncheckAllButMe(navNotifications)
        }

    }

    private fun uncheckAllButMe(target: CheckedTextView?) {
        navSearch?.isChecked = false
        navMyTrips?.isChecked = false
        navProfile?.isChecked = false
        navNotifications?.isChecked = false
        target?.isChecked = true
    }

    @ExperimentalSplittiesApi
    private fun checkUserLogin() {
        if (AppPrefs.token.isBlank()) {
            finish()
            start<AuthActivity> { }
        }
    }

    private fun subscribeObservers() {
        viewModel.isAppVersionDeprecated.observe(this) { isDeprecated ->
            if (isDeprecated) DialogForceUpdate().show(supportFragmentManager, "")
        }
    }


    private fun setupActionBar() {
//        setSupportActionBar(tool_bar)
    }

    fun showTabLayout() {
        tab_layout.visibility = View.VISIBLE
    }

    fun hideTabLayout() {
        tab_layout.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQ_CODE_ADD_POST) {
            navMyTrips.performClick()
        }

    }
}

