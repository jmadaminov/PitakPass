package com.novatec.epitak_passenger.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.ui.auth.AuthActivity
import com.novatec.epitak_passenger.ui.edit_profile.EditProfileActivity
import com.novatec.epitak_passenger.ui.feedback.FeedbackActivity
import com.novatec.epitak_passenger.ui.interfaces.IOnSignOut
import com.novatec.epitak_passenger.ui.main.MainActivity
import com.novatec.epitak_passenger.ui.settings.SettingsActivity
import com.novatec.epitak_passenger.util.UserPrefs
import com.novatec.epitak_passenger.util.loadRound
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start


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
        nameSurname.text = "${UserPrefs.name} ${UserPrefs.surname}"
        phone.text = "+${UserPrefs.phone}"

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
        UserPrefs.prefs.edit().clear().apply()
        start<AuthActivity> {}
    }

    override fun onResume() {
        super.onResume()
        if (UserPrefs.avatar.isNotBlank()) profilePhoto.loadRound(UserPrefs.avatar)
        nameSurname.text = "${UserPrefs.name} ${UserPrefs.surname}"
    }
}

