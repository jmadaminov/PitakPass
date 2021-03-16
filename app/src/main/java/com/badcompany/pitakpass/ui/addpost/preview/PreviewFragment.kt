package com.badcompany.pitakpass.ui.addpost.preview

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.core.enums.EPostStatus
import com.badcompany.pitakpass.core.enums.EPostType
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.ui.addpost.AddPostViewModel
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_preview.*
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject


@AndroidEntryPoint
class PreviewFragment @Inject constructor() : Fragment(R.layout.fragment_preview) {

    private val activityViewModel: AddPostViewModel by activityViewModels()

    private val viewModel: PreviewViewModel by viewModels()

    lateinit var navController: NavController


    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        setupViews()
        navController = findNavController()

    }

    private fun setupViews() {
        if (activityViewModel.isEditing) {
            navBack.visibility = View.INVISIBLE
            postCreate.text = getString(R.string.update)
        }

        if (activityViewModel.placeFrom?.name == null && activityViewModel.placeFrom?.districtName == null) {
            labelFromRegion.isVisible = false
            labelFrom.text = activityViewModel.placeFrom?.regionName
        } else {
            labelFromRegion.isVisible = true
            labelFromRegion.text =
                activityViewModel.placeFrom?.regionName ?: activityViewModel.placeFrom?.name
            labelFrom.text = activityViewModel.placeFrom?.districtName
        }

        if (activityViewModel.placeTo?.name == null && activityViewModel.placeTo?.districtName == null) {
            labelToRegion.isVisible = false
            labelTo.text = activityViewModel.placeTo?.regionName
        } else {
            labelToRegion.isVisible = true
            labelToRegion.text =
                activityViewModel.placeTo?.regionName ?: activityViewModel.placeTo?.name
            labelTo.text = activityViewModel.placeTo?.districtName
        }

        var time = ""
        if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = getString(R.string.anytime)
        } else if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 18:00"
        } else if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 12:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00"
        } else if (!activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "6:00 - 00:00"
        } else if (!activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "12:00 - 00:00"
        } else if (!activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "18:00 - 00:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00, 18:00-00:00"
        } else if (!activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "6:00 - 18:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00, 12:00 - 00:00"
        } else if (activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "00:00 - 12:00, 18:00 - 00:00"
        } else if (activityViewModel.timeFirstPart && !activityViewModel.timeSecondPart && activityViewModel.timeThirdPart && !activityViewModel.timeFourthPart) {
            time = "00:00 - 6:00, 12:00 - 18:00"
        } else if (!activityViewModel.timeFirstPart && activityViewModel.timeSecondPart && !activityViewModel.timeThirdPart && activityViewModel.timeFourthPart) {
            time = "6:00 - 12:00, 18:00 - 00:00"
        }

        dateTime.text =
            getString(R.string.departure_date_and_time, activityViewModel.departureDate, time)

        priceAndSeat.text = getString(R.string.price_and_seats_format,
                                      activityViewModel.price.toString(),
                                      activityViewModel.seat.toString())


        if (activityViewModel.note.isNullOrBlank()) {
            note.visibility = View.GONE
        } else {
            note.visibility = View.VISIBLE
            note.text = activityViewModel.note
        }


    }


    @ExperimentalSplittiesApi
    private fun setupListeners() {


        navBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        layoutDestinations.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToDestinationsFragment(
                true))
        }
        dateTime.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToDateTimeFragment(
                true))
        }
        priceAndSeat.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToPriceAndSeatFragment(
                true))
        }
        note.setOnClickListener {
            navController.navigate(PreviewFragmentDirections.actionPreviewFragmentToCarAndTextFragment(
                true))
        }

        postCreate.setOnClickListener {
            viewModel.createPassengerPost(PassengerPost(null,
                                                        activityViewModel.placeFrom!!,
                                                        activityViewModel.placeTo!!,
                                                        activityViewModel.price!!,
                                                        activityViewModel.departureDate!!,
                                                        null,
                                                        null,
                                                        null,
                                                        activityViewModel.timeFirstPart,
                                                        activityViewModel.timeSecondPart,
                                                        activityViewModel.timeThirdPart,
                                                        activityViewModel.timeFourthPart,
                                                        null,
                                                        null,
                                                        activityViewModel.note!!,
                                                        EPostStatus.CREATED,
                                                        activityViewModel.seat!!,
                                                        0,
                                                        EPostType.PASSENGER_SM))
        }

    }

    @ExperimentalSplittiesApi
    private fun setupObservers() {
        viewModel.createResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(scrollView, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(scrollView,
                                  response.err.localizedMessage!!,
                                  Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultWrapper.Success -> {
                    postCreate.stopAnimation()
                    requireActivity().finish()
                }
                ResultWrapper.InProgress -> {
                    postCreate.startAnimation()
                }
            }.exhaustive

        })

    }


    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
    }


}




