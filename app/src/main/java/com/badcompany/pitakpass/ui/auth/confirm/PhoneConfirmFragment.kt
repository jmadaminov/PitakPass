package com.badcompany.pitakpass.ui.auth.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.badcompany.pitakpass.App
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.AuthBody
import com.badcompany.pitakpass.ui.auth.AuthActivity
import com.badcompany.pitakpass.ui.main.MainActivity
import com.badcompany.pitakpass.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_phone_confirm.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import splitties.init.appCtx
import splitties.preferences.edit
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PhoneConfirmFragment @Inject constructor() :
    Fragment(R.layout.fragment_phone_confirm) {

    private val viewModel: PhoneConfirmViewModel by viewModels()

    val args: PhoneConfirmFragmentArgs by navArgs()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()


        navController = findNavController()
        confirm.isEnabled = true

        code.setText(args.password)

        confirm.setOnClickListener {
            viewModel.confirm(args.phone, code.text.toString())
        }

    }

    @ExperimentalSplittiesApi
    private fun setupObservers() {
        viewModel.confirmResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    confirm.revertAnimation()
                    if (response.code == Constants.errPhoneFormat) {
                        code.error = getString(R.string.incorrect_phone_number_format)
//                        errorMessage.visibility = View.VISIBLE
//                        errorMessage.text = response.message
                    } else {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = response.message
                    }
                }
                is ErrorWrapper.SystemError -> {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = response.err.message
                    confirm.revertAnimation()
                }
                is ResultWrapper.Success -> {
                    confirm.revertAnimation()
                    saveCredentials(response)
                    context?.start<MainActivity> { }
                }
                ResultWrapper.InProgress -> {
                    errorMessage.visibility = View.INVISIBLE
                    confirm.startAnimation()
                }
            }.exhaustive

        })
    }

    @ExperimentalSplittiesApi
    private fun saveCredentials(response: ResultWrapper.Success<AuthBody>) {
        AppPreferences.edit {
            token = response.value.jwt!!
            name = response.value.name!!
            surname = response.value.surname!!
            phone = response.value.phoneNum!!
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AuthActivity).showActionBar()
    }


}




