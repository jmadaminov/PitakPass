package com.badcompany.pitakpass.ui.main.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.auth.AuthActivity
import com.badcompany.pitakpass.ui.edit_profile.EditProfileActivity
import com.badcompany.pitakpass.ui.feedback.FeedbackActivity
import com.badcompany.pitakpass.ui.interfaces.IOnSignOut
import com.badcompany.pitakpass.ui.main.MainActivity
import com.badcompany.pitakpass.util.AppPrefs
import com.badcompany.pitakpass.util.loadImageUrl
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notifications.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import splitties.preferences.edit
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment @Inject constructor() : Fragment(R.layout.fragment_notifications) {

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        subscribeObservers()
        setupViews()
    }

    @ExperimentalSplittiesApi
    private fun setupViews() {

    }


    private fun subscribeObservers() {


    }


    private fun setupListeners() {




    }



}

