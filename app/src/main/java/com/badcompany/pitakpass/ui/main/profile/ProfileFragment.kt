package com.badcompany.pitakpass.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.auth.AuthActivity
import com.badcompany.pitakpass.ui.feedback.FeedbackActivity
import com.badcompany.pitakpass.ui.interfaces.IOnSignOut
import com.badcompany.pitakpass.ui.main.MainActivity
import com.badcompany.pitakpass.util.AppPrefs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import splitties.preferences.edit
import javax.inject.Inject

//@FlowPreview
//@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment @Inject constructor() : Fragment(R.layout.fragment_profile), IOnSignOut {

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val viewModel: ProfileViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

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
        cardDriver.setBackgroundResource(R.drawable.stroke_rounded_bottom_corners)
        nameSurname.text = "${AppPrefs.name} ${AppPrefs.surname}"
        phone.text = "+${AppPrefs.phone}"

    }


    private fun subscribeObservers() {


    }


    private fun setupListeners() {

        btnFeedback.setOnClickListener {
            start<FeedbackActivity> {}
        }

        signOut.setOnClickListener {
            val dialog = DialogSignOut()
            dialog.setTargetFragment(this, 88)
            dialog.show(childFragmentManager, "")
        }

    }


    @ExperimentalSplittiesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CODE_ADD_CAR && resultCode == RESULT_OK) {
//            Log.d("ON ACTIVITY RESULT   ", "  $resultCode")
//            viewModel.getCarList(AppPreferences.token)
//        }
    }

    override fun onSignOut() {
        requireActivity().finish()
        AppPrefs.edit {
            token = ""
            name = ""
            surname = ""
            phone = ""
        }
        start<AuthActivity> {}
    }
}

