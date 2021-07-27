package com.novatec.epitak_passenger.ui.driver_post.jump_in

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels

import com.google.android.material.snackbar.Snackbar
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.ui.viewgroups.ActivePostItem
import com.novatec.epitak_passenger.util.ErrorWrapper
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.util.exhaustive
import com.novatec.epitak_passenger.viewobjects.DriverPostViewObj
import com.novatec.epitak_passenger.viewobjects.UserOfferViewObj
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_jump_in.*
import splitties.experimental.ExperimentalSplittiesApi

const val ARG_DRIVER_POST = "DRIVER_POST"

@AndroidEntryPoint
class DialogJoinARideFragment : DialogFragment() {
    private val adapter = GroupAdapter<GroupieViewHolder>()

    private lateinit var driverPost: DriverPostViewObj
    val viewModel: JumpInViewModel by viewModels()

    override fun getTheme() = R.style.Theme_Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        driverPost = requireArguments().getParcelable(ARG_DRIVER_POST)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_jump_in, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        edtPrice.hint =
//            DecimalFormat("#,###").format(driverPost.price) + " " + getString(R.string.sum)
        rvMyPosts.adapter = adapter
        attachListeners()
        subscribeObservers()
        driverPost.myLastOffer?.let { showOfferSent(driverPost.myLastOffer!!) } ?: run {
            viewModel.getActivePosts()
        }

    }

    private fun showOfferSent(myLastOffer: UserOfferViewObj) {
        viewModel.offeringPostId.value = myLastOffer.repliedPostId
        rvContainer.isVisible = false
        lblSelectPost.isVisible = false
        cardLastOffer.isVisible = true
        tvLastOfferPrice.text = getString(R.string.price) + " " + myLastOffer.priceInt.toString()
        tvLastOfferRepliedPostId.text =
            getString(R.string.attached_post_id) + " " + myLastOffer.repliedPostId.toString()
        tvLastOfferMessage.text = getString(R.string.message) + " " + myLastOffer.message
        btnSendOffer.text = getString(R.string.update_offer)
    }


    private fun subscribeObservers() {

        viewModel.isOffering.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) btnSendOffer.startAnimation() else btnSendOffer.revertAnimation()

        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->

            Snackbar.make(rl_parent, errorMessage, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.hasFinished.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) dismiss()
        }

        viewModel.activePostsResponse.observe(viewLifecycleOwner) {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    Snackbar.make(
                        rl_parent,
                        response.message ?: getString(R.string.response_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(
                        rl_parent,
                        response.err.localizedMessage ?: getString(R.string.system_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultWrapper.Success -> {
                    loadData(response.value.filter { myPost ->
                        myPost.departureDate == driverPost.departureDate && myPost.postStatus.isOfferableForPassenger()
                    })
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        }
    }

    @ExperimentalSplittiesApi
    private fun loadData(orders: List<PassengerPost>) {
        adapter.clear()
        if (orders.isEmpty()) {
            lblSelectPost.text = getString(R.string.we_will_create_an_order_for_you)
            rvContainer.visibility = View.GONE
        } else {
            lblSelectPost.text = getString(R.string.select_your_trip_if_you_have_one)
            rvContainer.visibility = View.VISIBLE
        }

        orders.forEach { post ->
            adapter.add(ActivePostItem(post) {
                lblSelectPost.visibility = View.GONE
                tvSelectedPost.visibility = View.VISIBLE
                ivClearSelected.visibility = View.VISIBLE
                tvSelectedPost.text = getString(R.string.offering_post_id, post.id)
                post.id?.let {
                    viewModel.setOfferingPost(it)
                } ?: run {
                    viewModel.clearOfferingPost()
                }

            })
        }

    }

    private fun attachListeners() {

        edtPrice.doOnTextChanged { text, start, before, count ->
        }

        btnSendOffer.setOnClickListener {
            viewModel.joinARide(
                driverPost.id,
                if (edtPrice.text.isNullOrBlank()) driverPost.price else edtPrice.text.toString()
                    .toInt(),
                messageInput.text.toString(),
                tvSeats.text.toString().toInt(),
                driverPost
            )
        }

        tvSubtractSeat.setOnClickListener {
            if (tvSeats.text.toString().toInt() > 1)
                tvSeats.text = (tvSeats.text.toString().toInt() - 1).toString()
        }

        tvAddSeat.setOnClickListener {
            if (driverPost.availableSeats > tvSeats.text.toString().toInt())
                tvSeats.text = (tvSeats.text.toString().toInt() + 1).toString()
        }

        ivClearSelected.setOnClickListener {
            viewModel.clearOfferingPost()
            lblSelectPost.visibility = View.VISIBLE
            tvSelectedPost.visibility = View.GONE
            ivClearSelected.visibility = View.GONE
        }
    }
}