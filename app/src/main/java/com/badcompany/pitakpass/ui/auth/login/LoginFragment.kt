package com.badcompany.pitakpass.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject



@AndroidEntryPoint
class LoginFragment @Inject constructor() :
    Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()


    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()

    }

    private fun attachListeners() {
        login.setOnClickListener {
            viewModel.login(phone.text.toString())
        }
    }

    private fun setupViews() {
        navController = findNavController()
        setHasOptionsMenu(true)

        login.isEnabled = true
    }

    override fun onResume() {
        super.onResume()
    }

    private fun subscribeObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading ?: return@observe) {
                errorMessage.visibility = View.INVISIBLE
                login.startAnimation()
            } else {
                login.revertAnimation()
            }

        }


        viewModel.loginResponse.observe(viewLifecycleOwner) {
            val response = it ?: return@observe

            when (response) {
                is ResponseError -> {
                    login.revertAnimation()
                    if (response.code == -1) {
                        val action =
                            LoginFragmentDirections.actionNavLoginFragmentToNavRegisterFragment(
                                viewModel.phoneNum)
                        findNavController().navigate(action)
                    } else if (response.code == Constants.errPhoneFormat) {
                        phone.error = getString(R.string.incorrect_phone_number_format)
                    } else {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = response.message
                    }
                }
                is ResponseSuccess -> {
                    login.revertAnimation()
                    val action =
                        LoginFragmentDirections.actionNavLoginFragmentToNavPhoneConfirmFragment(
                            password = response.value?.password,
                            phone = viewModel.phoneNum)
                    findNavController().navigate(action)
                }
            }.exhaustive

//            when (response) {
//                is ErrorWrapper.ResponseError -> {
//                    login.revertAnimation()
//                    if (response.code == -1) {
//                        val action =
//                            LoginFragmentDirections.actionNavLoginFragmentToNavRegisterFragment(
//                                viewModel.phoneNum)
//                        findNavController().navigate(action)
//                    } else if (response.code == Constants.errPhoneFormat) {
//                        phone.error = getString(R.string.incorrect_phone_number_format)
////                        errorMessage.visibility = View.VISIBLE
////                        errorMessage.text = response.message
//                    } else {
//                        errorMessage.visibility = View.VISIBLE
//                        errorMessage.text = response.message
//                    }
//                }
//                is ErrorWrapper.SystemError -> {
//                    errorMessage.visibility = View.VISIBLE
//                    errorMessage.text = response.err.localizedMessage
//                    login.revertAnimation()
//                }
//                is ResultWrapper.Success -> {
//
//                }
//                ResultWrapper.InProgress -> {
//                    errorMessage.visibility = View.INVISIBLE
//                    login.startAnimation()
//                }
//            }.exhaustive

        }
    }

}




