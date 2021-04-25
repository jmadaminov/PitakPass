package com.novatec.pitakpass.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.novatec.pitakpass.R
import com.novatec.pitakpass.ui.auth.AuthActivity
import com.novatec.pitakpass.ui.edit_profile.EditProfileActivity
import com.novatec.pitakpass.ui.feedback.FeedbackActivity
import com.novatec.pitakpass.ui.interfaces.IOnSignOut
import com.novatec.pitakpass.ui.main.MainActivity
import com.novatec.pitakpass.ui.settings.SettingsActivity
import com.novatec.pitakpass.util.AppPrefs
import com.novatec.pitakpass.util.load
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), IOnSignOut {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupListeners()
        subscribeObservers()
        setupViews()
    }

    @ExperimentalSplittiesApi
    private fun setupViews() {
        (activity as MainActivity).hideTabLayout()
//        cardProfile.setBackgroundResource(R.drawable.stroke_rounded_bottom_corners)
        nameSurname.text = "${AppPrefs.name} ${AppPrefs.surname}"
        phone.text = "+${AppPrefs.phone}"

    }


    private fun subscribeObservers() {

    }


    private fun setupListeners() {

        btnFeedback.setOnClickListener {
            start<FeedbackActivity> {}
        }
        settings.setOnClickListener {
            start<SettingsActivity> {}
        }

        ivEditProfile.setOnClickListener {
            start<EditProfileActivity> {}
        }

        signOut.setOnClickListener {
            val dialog = DialogSignOut()
            dialog.setTargetFragment(this, 88)
            dialog.show(parentFragmentManager, "")
        }


    }


    @ExperimentalSplittiesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onSignOut() {
        requireActivity().finish()
        AppPrefs.prefs.edit().clear().apply()
        start<AuthActivity> {}
    }

    override fun onResume() {
        super.onResume()
        if (AppPrefs.avatar.isNotBlank()) profilePhoto.load(AppPrefs.avatar)
        nameSurname.text = "${AppPrefs.name} ${AppPrefs.surname}"
    }
}

