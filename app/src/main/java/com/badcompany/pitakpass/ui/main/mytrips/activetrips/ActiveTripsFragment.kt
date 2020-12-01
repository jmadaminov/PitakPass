package com.badcompany.pitakpass.ui.main.mytrips.activetrips

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.ui.passenger_post.PassengerPostActivity
import com.badcompany.pitakpass.ui.passenger_post.PassengerPostActivity.Companion.REQ_POST_MANIPULATED
import com.badcompany.pitakpass.ui.viewgroups.ActivePostItem
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.exhaustive
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_active_trips.*
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
@AndroidEntryPoint
class ActiveTripsFragment : Fragment(R.layout.fragment_active_trips) {


    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewmodel: ActiveTripsViewModel by viewModels()

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupViews()
        setupListeners()
        subscribeObservers()
        viewmodel.getActivePosts()
    }


    private fun setupViews() {
        activeOrdersList.adapter = adapter

    }

    @ExperimentalSplittiesApi
    private fun setupListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            noActiveOrdersTxt.visibility = View.GONE
            viewmodel.getActivePosts()
        }
    }

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {

        viewmodel.activePostsResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    swipeRefreshLayout.isRefreshing = false
                    noActiveOrdersTxt.visibility = View.VISIBLE
                    noActiveOrdersTxt.text = response.message
                    activeOrdersList.visibility = View.INVISIBLE

                }
                is ErrorWrapper.SystemError -> {
                    noActiveOrdersTxt.visibility = View.VISIBLE
                    noActiveOrdersTxt.text = response.err.localizedMessage
                    activeOrdersList.visibility = View.INVISIBLE
                    swipeRefreshLayout.isRefreshing = false

                }
                is ResultWrapper.Success -> {
                    noActiveOrdersTxt.visibility = View.INVISIBLE
                    activeOrdersList.visibility = View.VISIBLE
                    swipeRefreshLayout.isRefreshing = false
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                    noActiveOrdersTxt.visibility = View.INVISIBLE
                    activeOrdersList.visibility = View.INVISIBLE
                    swipeRefreshLayout.isRefreshing = true
                }
            }.exhaustive
        })

    }

    @ExperimentalSplittiesApi
    private fun loadData(orders: List<PassengerPost>) {
        adapter.clear()
        if (orders.isEmpty()) {
            noActiveOrdersTxt.visibility = View.VISIBLE
            noActiveOrdersTxt.text = getString(R.string.no_active_orders)
        } else noActiveOrdersTxt.visibility = View.GONE

        orders.forEach { post ->
            adapter.add(ActivePostItem(post /*, onOrderActionListener*/) {
                startActivityForResult(Intent(requireActivity(),
                                              PassengerPostActivity::class.java).apply {
                    putExtra(PassengerPostActivity.EXTRA_POST_ID, post.id)
                }, REQ_POST_MANIPULATED)
            })
        }
        adapter.notifyDataSetChanged()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_POST_MANIPULATED) {
                viewmodel.getActivePosts()
            }
        }
    }


}