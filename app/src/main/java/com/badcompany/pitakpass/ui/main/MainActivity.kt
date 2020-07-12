package com.badcompany.pitakpass.ui.main

import android.animation.LayoutTransition
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import com.badcompany.pitakpass.App
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.di.viewmodels.MainViewModelFactory
import com.badcompany.pitakpass.fragments.MainNavHostFragment
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.ui.addpost.AddPostActivity
import com.badcompany.pitakpass.ui.auth.AuthActivity
import com.badcompany.pitakpass.ui.main.mytrips.MyTripsFragment
import com.badcompany.pitakpass.ui.main.profile.ProfileFragment
import com.badcompany.pitakpass.ui.main.searchtrip.SearchTripFragment
import com.badcompany.pitakpass.util.AppPreferences
import com.badcompany.pitakpass.util.BOTTOM_NAV_BACKSTACK_KEY
import com.badcompany.pitakpass.util.BottomNavController
import com.badcompany.pitakpass.util.setUpNavigation
import kotlinx.android.synthetic.main.activity_main.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity(), BottomNavController.OnNavigationGraphChanged,
    BottomNavController.OnNavigationReselectedListener {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    @Inject
    @Named("MainFragmentFactory")
    lateinit var fragmentFactory: FragmentFactory

    override fun inject() {
        (application as App).mainComponent()
            .inject(this)
    }

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_fragments_container,
            R.id.navSearchTripFragment,
            this)
    }

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        checkUserLogin()
        inject()
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app_bar.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        setupActionBar()
        setupListeners()
        subscribeObservers()
        onRestoreInstanceState()
        setupBottomNavigationView(savedInstanceState)

    }

    private fun setupListeners() {
        addPost.setOnClickListener {
            start<AddPostActivity>()
        }
    }

    @ExperimentalSplittiesApi
    private fun checkUserLogin() {
        if (AppPreferences.token.isBlank()) {
            start<AuthActivity> { }
        }
    }

    private fun setupBottomNavigationView(savedInstanceState: Bundle?) {
        nav_view.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.setupBottomNavigationBackStack(null)
            bottomNavController.onNavigationItemSelected()
        } else {
            (savedInstanceState[BOTTOM_NAV_BACKSTACK_KEY] as IntArray?)?.let { items ->
                val backstack = BottomNavController.BackStack()
                backstack.addAll(items.toTypedArray())
                bottomNavController.setupBottomNavigationBackStack(backstack)
            }
        }
    }


    private fun subscribeObservers() {
//        TODO("Not yet implemented")
    }

    override fun onReselectNavItem(
        navController: NavController,
        fragment: Fragment
    ) {
        Log.d(TAG, "logInfo: onReSelectItem")
        when (fragment) {
            is SearchTripFragment -> {
//                navController.navigate(R.id.action_navSearchTripFragment_self)
            }

            is MyTripsFragment -> {
//                navController.navigate(R.id.action_nav_menu_my_trips_self)
            }

            is ProfileFragment -> {
//                navController.navigate(R.id.action_nav_menu_profile_self)
            }
            else -> {
                // do nothing
            }
        }
    }

    private fun onRestoreInstanceState() {
        val host = supportFragmentManager.findFragmentById(R.id.main_fragments_container)
        host?.let { } ?: createNavHost()
    }

    private fun createNavHost() {
        val navHost = MainNavHostFragment.create(R.navigation.main_nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragments_container, navHost, getString(
                R.string.MainNavHost))
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }

    override fun onGraphChange() {

    }

    override fun onBackPressed() = bottomNavController.onBackPressed()

    private fun setupActionBar() {
        setSupportActionBar(tool_bar)
    }

    fun showTabLayout() {
        tab_layout.visibility = View.VISIBLE
    }

    fun hideTabLayout() {
        tab_layout.visibility = View.GONE
    }


}

