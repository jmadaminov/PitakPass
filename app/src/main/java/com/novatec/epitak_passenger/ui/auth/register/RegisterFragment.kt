package com.novatec.epitak_passenger.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.novatec.epitak_passenger.App
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.domain.model.User
import com.novatec.epitak_passenger.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment @Inject constructor() :
    Fragment(R.layout.fragment_register) {

    val args: RegisterFragmentArgs by navArgs()

    lateinit var navController: NavController

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()
        phone.setText(args.phone)

        register.isEnabled = true

        navController = findNavController()

        register.setOnClickListener {
            viewModel.register(User(phone.text.toString().numericOnly(),
                                    name.text.toString(),
                                    surname.text.toString(),
                                    App.uuid,
                                    Constants.ROLE_PASSENGER))
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupObservers() {

        viewModel.registerFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            register.isEnabled = loginState.isDataValid

//            if (loginState.usernameError != null) {
//                username.error = getString(loginState.usernameError)
//            }
//            if (loginState.passwordError != null) {
//                password.error = getString(loginState.passwordError)
//            }
        })

        viewModel.regResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
//
////            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    register.revertAnimation()
                    /*  if (response.code == -1) {
                          val action =
                              RegisterFragmentDirections.actionNavRegisterFragmentToNavPhoneConfirmFragment(
                                  response., viewModel.phoneNum )
                          findNavController().navigate(action)
                      } else */if (response.code == Constants.errPhoneFormat) {
                        phone.error = getString(R.string.incorrect_phone_number_format)
//                        errorMessage.visibility = View.VISIBLE
//                        errorMessage.text = response.message
                    } else {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = response.message
                    }
                }
                is ErrorWrapper.SystemError -> {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "SYSTEM ERROR"
                    register.revertAnimation()
                }
                is ResultWrapper.Success -> {
                    register.revertAnimation()
                    val action =
                        RegisterFragmentDirections.actionNavRegisterFragmentToNavPhoneConfirmFragment(
                            response.value,
                            args.phone)
                    findNavController().navigate(action)
                }
                ResultWrapper.InProgress -> {
                    errorMessage.visibility = View.INVISIBLE
                    register.startAnimation()
                }
            }.exhaustive


        })
    }


}


