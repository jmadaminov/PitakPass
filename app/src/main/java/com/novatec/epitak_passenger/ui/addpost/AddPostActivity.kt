package com.novatec.epitak_passenger.ui.addpost

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.ui.BaseActivity
import com.novatec.epitak_passenger.ui.addpost.destinations.DestinationsFragmentDirections
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.viewobjects.PassengerPostViewObj
import kotlinx.android.synthetic.main.activity_add_post.*
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

        subscribeObservers()
        setupListeners()

    }

    override fun onResume() {
        super.onResume()
        checkIfEditing(intent.getParcelableExtra(Constants.TXT_PASSENGER_POST))

    }

    private fun checkIfEditing(passengerPostViewObj: PassengerPostViewObj?) {
        if (passengerPostViewObj != null) {
            viewModel.id = passengerPostViewObj.id

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
                                      passengerPostViewObj.to.regionName,
                                      passengerPostViewObj.to.name)

            viewModel.timeFirstPart = passengerPostViewObj.timeFirstPart
            viewModel.timeSecondPart = passengerPostViewObj.timeSecondPart
            viewModel.timeThirdPart = passengerPostViewObj.timeThirdPart
            viewModel.timeFourthPart = passengerPostViewObj.timeFourthPart
            viewModel.departureDate = passengerPostViewObj.departureDate
            viewModel.note = passengerPostViewObj.remark

            add_post_fragments_container.findNavController()
                .navigate(DestinationsFragmentDirections.actionDestinationsFragmentToPreviewFragmentClearBackstack())
        }

    }


    private fun setupListeners() {
        tvClose.setOnClickListener {
            finish()
        }
        ivBack.setOnClickListener {
            if (!findNavController(R.id.add_post_fragments_container).popBackStack()) {
                finish()
            }
        }

    }

    private fun subscribeObservers() {


    }


    private fun setupActionBar() {
        setSupportActionBar(toolbar)
    }


}
