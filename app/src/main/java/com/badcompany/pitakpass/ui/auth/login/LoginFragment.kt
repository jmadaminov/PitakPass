package com.badcompany.pitakpass.ui.auth.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.exhaustive
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
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
        setHasOptionsMenu(true)

        setupObservers()

        /*    phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

            phone.afterTextChanged {
                viewModel.loginDataChanged(
                    phone.text.toString()
    //                password.text.toString()
                )
            }

            password.apply {
                afterTextChanged {
                    viewModel.loginDataChanged(
                        phone.text.toString(),
                        password.text.toString()
                    )
                }

                setOnEditorActionListener { _, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE ->
                            viewModel.login(
                                phone.text.toString(),
                                password.text.toString()
                            )
                    }
                    false
                }

                login.setOnClickListener {
                    viewModel.login(phone.text.toString(), password.text.toString())
                }
            }
    */

        navController = findNavController()

        login.isEnabled = true
        login.setOnClickListener {
            viewModel.login(phone.text.toString())
//            navController.navigate(R.id.action_navLoginFragment_to_navRegisterFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AuthActivity).hideActionBar()
    }

    private fun setupObservers() {
//        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
//            val loginState = it ?: return@Observer
//            if (loginState.phoneError != null) {
//                phone.error = getString(loginState.phoneError)
//            }
//
//        })

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    login.revertAnimation()
                    if (response.code == -1) {
                        val action =
                            LoginFragmentDirections.actionNavLoginFragmentToNavRegisterFragment(
                                viewModel.phoneNum)
                        findNavController().navigate(action)
                    } else if (response.code == Constants.errPhoneFormat) {
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
                    errorMessage.text = response.err.localizedMessage
                    login.revertAnimation()
                }
                is ResultWrapper.Success -> {
                    login.revertAnimation()
                    val action =
                        LoginFragmentDirections.actionNavLoginFragmentToNavPhoneConfirmFragment(
                            response.value,
                            viewModel.phoneNum)
                    findNavController().navigate(action)
                }
                ResultWrapper.InProgress -> {
                    errorMessage.visibility = View.INVISIBLE
                    login.startAnimation()
                }
            }.exhaustive

        })
    }

}



