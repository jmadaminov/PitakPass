package com.badcompany.pitakpass.ui.addpost

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.*
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.viewobjects.PassengerPostViewObj
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi


class AddPostActivity : BaseActivity() {

    private val viewModel: AddPostViewModel by viewModels()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        setupActionBar()
//        onRestoreInstanceState()

        subscribeObservers()
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        checkIfEditing(intent.getParcelableExtra(Constants.TXT_PASSENGER_POST))

    }

    private fun checkIfEditing(passengerPostViewObj: PassengerPostViewObj?) {
        if (passengerPostViewObj != null) {
            viewModel.isEditing = true
            viewModel.price = passengerPostViewObj.price
            viewModel.seat = passengerPostViewObj.seat
            viewModel.placeFrom = Place(passengerPostViewObj.from.districtId,
                                        passengerPostViewObj.from.regionId,
                                        passengerPostViewObj.from.lat,
                                        passengerPostViewObj.from.lon,
                                        passengerPostViewObj.from.regionName,
                                        passengerPostViewObj.from.name)

            viewModel.placeTo = Place(passengerPostViewObj.to.districtId,
                                      passengerPostViewObj.to.regionId,
                                      passengerPostViewObj.to.lat,
                                      passengerPostViewObj.to.lon,
                                      passengerPostViewObj.from.regionName,
                                      passengerPostViewObj.from.name)

            viewModel.timeFirstPart = passengerPostViewObj.timeFirstPart
            viewModel.timeSecondPart = passengerPostViewObj.timeSecondPart
            viewModel.timeThirdPart = passengerPostViewObj.timeThirdPart
            viewModel.timeFourthPart = passengerPostViewObj.timeFourthPart
            viewModel.departureDate = passengerPostViewObj.departureDate
            viewModel.note = passengerPostViewObj.remark

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.previewFragment, true)
                .build()

            findNavController(R.id.add_post_fragments_container)
                .navigate(R.id.action_destinationsFragment_to_previewFragment,
                          null,
                          navOptions)
        }

    }


    private fun setupListeners() {


    }

    private fun subscribeObservers() {


    }


//    var host: Fragment? = null
//    lateinit var navHost: Fragment
//
//    private fun onRestoreInstanceState() {
//        host = supportFragmentManager.findFragmentById(R.id.add_post_fragments_container)
//        host?.let { /*do nothing*/ } ?: createNavHost()
//    }
//
//    private fun createNavHost() {
//        navHost = AddPostNavHostFragment.create(R.navigation.add_post_nav_graph)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.add_post_fragments_container, navHost, getString(R.string.AuthNavHost))
//            .setPrimaryNavigationFragment(navHost)
//            .commit()
//    }


    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
