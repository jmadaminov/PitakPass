package com.novatec.epitak_passenger.ui.main

import android.animation.LayoutTransition
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.CheckedTextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.ui.BaseActivity
import com.novatec.epitak_passenger.ui.addpost.AddPostActivity
import com.novatec.epitak_passenger.ui.auth.AuthActivity
import com.novatec.epitak_passenger.ui.intro.IntroActivity
import com.novatec.epitak_passenger.ui.main.mytrips.MyTripsFragment
import com.novatec.epitak_passenger.ui.main.notifications.NotificationsFragment
import com.novatec.epitak_passenger.ui.main.profile.ProfileFragment
import com.novatec.epitak_passenger.ui.main.searchtrip.SearchTripFragment
import com.novatec.epitak_passenger.ui.passenger_post.PassengerPostActivity
import com.novatec.epitak_passenger.ui.passenger_post.PassengerPostActivity.Companion.EXTRA_POST_ID
import com.novatec.epitak_passenger.util.AppPrefs
import com.novatec.epitak_passenger.util.ContextUtils.setLocale
import com.novatec.epitak_passenger.util.UserPrefs
import com.takusemba.spotlight.OnSpotlightListener
import com.takusemba.spotlight.OnTargetListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.effet.RippleEffect
import com.takusemba.spotlight.shape.Circle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_btn_target_layout.view.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit

class MainActivity : BaseActivity() {

    companion object {
        private val REQ_CODE_ADD_POST = 98
        var displayMetrics = DisplayMetrics()
    }


    private lateinit var spotlight: Spotlight
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    private var notificationPostId: Long? = null

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        display?.getMetrics(displayMetrics)
        setLocale(AppPrefs.language, this)
        setTheme(R.style.NoActionBar)
        if (AppPrefs.isFirstTime) {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        } else checkUserLogin()
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


        if (UserPrefs.token.isNotBlank() && !AppPrefs.hasSeenTutorial) {
            showAddBtnTutorial()
            AppPrefs.edit {
                hasSeenTutorial = true
            }
        }
    }

    private fun showAddBtnTutorial() {

        addPost.doOnPreDraw {

            spotlight = Spotlight.Builder(this)
                .setTargets(buildAddBtnTarget())
                .setBackgroundColor(ContextCompat.getColor(this, R.color.black_80_opacity))
                .setDuration(1000L)
                .setAnimation(DecelerateInterpolator(2f))
//                .setContainer(viewGroup)
                .setOnSpotlightListener(object : OnSpotlightListener {
                    override fun onStarted() {
                    }

                    override fun onEnded() {
                    }
                })
                .build()
            spotlight.start()


        }
    }

    private fun buildAddBtnTarget(): com.takusemba.spotlight.Target {
        val firstRoot = View.inflate(this, R.layout.add_btn_target_layout, null)

        firstRoot.tvGotIt.setOnClickListener {
            spotlight.finish()
        }
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        return com.takusemba.spotlight.Target.Builder()
            .setAnchor(addPost.x + addPost.width / 2, (addPost.y + addPost.height - 10).toFloat())
            .setShape(Circle((addPost.width * 0.75).toFloat()))
            .setEffect(RippleEffect(100f, 200f, Color.argb(30, 124, 255, 90)))
            .setOverlay(firstRoot)
            .setOnTargetListener(object : OnTargetListener {
                override fun onStarted() {
//                    makeText(this@MainActivity, "first target is started", LENGTH_SHORT).show()
                }

                override fun onEnded() {
//                    makeText(this@MainActivity, "first target is ended", LENGTH_SHORT).show()
                }
            })
            .build()
    }


    private fun setupListeners() {
        nav_host_fragment.childFragmentManager.addOnBackStackChangedListener {
            val fragments = nav_host_fragment.childFragmentManager.fragments

            if (fragments.isNotEmpty()) {
                when (fragments[0]) {
                    is SearchTripFragment -> {
                        uncheckAllButMe(navSearch)
                    }
                    is MyTripsFragment -> {
                        uncheckAllButMe(navMyTrips)

                    }
                    is NotificationsFragment -> {
                        uncheckAllButMe(navNotifications)

                    }
                    is ProfileFragment -> {
                        uncheckAllButMe(navProfile)

                    }
                }
            }
        }

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
        if (UserPrefs.token.isBlank()) {
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
        tab_layout?.visibility = View.VISIBLE
    }

    fun hideTabLayout() {
        tab_layout?.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQ_CODE_ADD_POST) {
            navMyTrips.performClick()
        }

    }
}

