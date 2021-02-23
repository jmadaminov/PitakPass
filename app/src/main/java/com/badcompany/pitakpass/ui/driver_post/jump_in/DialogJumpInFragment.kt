package com.badcompany.pitakpass.ui.driver_post.jump_in

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.ui.viewgroups.ActivePostItem
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.exhaustive
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_jump_in, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        edtPrice.hint =
//            DecimalFormat("#,###").format(driverPost.price) + " " + getString(R.string.sum)
        rvMyPosts.adapter = adapter
        attachListeners()
        subscribeObservers()
        viewModel.getActivePosts()

    }

    private fun subscribeObservers() {

        viewModel.isOffering.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) btnSendOffer.startAnimation() else btnSendOffer.revertAnimation()
        })

        viewModel.hasFinished.observe(viewLifecycleOwner, { hasFinished ->
            if (hasFinished) dismiss()
        })

        viewModel.activePostsResponse.observe(viewLifecycleOwner, {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.ResponseError -> {
                }
                is ErrorWrapper.SystemError -> {
                }
                is ResultWrapper.Success -> {
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        })
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
                viewModel.setOfferingPost(post.id)

            })
        }

    }

    private fun attachListeners() {

        edtPrice.doOnTextChanged { text, start, before, count ->
        }

        btnSendOffer.setOnClickListener {
            viewModel.joinARide(driverPost.id,
                                if (edtPrice.text.isNullOrBlank()) driverPost.price else edtPrice.text.toString()
                                    .toInt(),
                                messageInput.text.toString(),
                                tvSeats.text.toString().toInt(),
                                driverPost)
        }

        tvSubtractSeat.setOnClickListener {
            if (tvSeats.text.toString().toInt() > 1)
                tvSeats.text = (tvSeats.text.toString().toInt() - 1).toString()
        }

        tvAddSeat.setOnClickListener {
            if (driverPost.seat > tvSeats.text.toString().toInt())
                tvSeats.text = (tvSeats.text.toString().toInt() + 1).toString()
        }

        ivClearSelected.setOnClickListener {
            viewModel.setOfferingPost(null)
            lblSelectPost.visibility = View.VISIBLE
            tvSelectedPost.visibility = View.GONE
            ivClearSelected.visibility = View.GONE
        }
    }
}