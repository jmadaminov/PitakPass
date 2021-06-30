package com.novatec.epitak_passenger.ui.auth.login

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment @Inject constructor() : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()


    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()

        Activity.RESULT_CANCELED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun attachListeners() {
        tvGo.setOnClickListener { viewModel.login(phone.text.toString()) }

        phone.doOnTextChanged { text, start, before, count ->
            tvGo.isEnabled = phone.unmaskedText.length == 9
        }

        tv1.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("1")) }
        tv2.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("2")) }
        tv3.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("3")) }
        tv4.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("4")) }
        tv5.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("5")) }
        tv6.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("6")) }
        tv7.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("7")) }
        tv8.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("8")) }
        tv9.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("9")) }
        tv0.setOnClickListener { phone.setMaskedText(phone.unmaskedText.plus("0")) }

        ivBackspace.setOnClickListener {
            if (phone.unmaskedText.isEmpty()) return@setOnClickListener
            phone.setMaskedText(phone.unmaskedText.substring(0, phone.unmaskedText.length - 1))
        }

    }

    private fun setupViews() {
        phone.isFocusable = false
        phone.isFocusableInTouchMode = false
        tvGo.isEnabled = false
        navController = findNavController()

//        login.isEnabled = true
    }


    private fun subscribeObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading ?: return@observe) {
                errorMessage.visibility = View.INVISIBLE
                progress.isVisible = true
            } else {
                progress.isVisible = false
            }
        }


        viewModel.loginResponse.observe(viewLifecycleOwner) {
            val response = it ?: return@observe

            when (response) {
                is ResponseError -> {
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
                    val action =
                        LoginFragmentDirections.actionNavLoginFragmentToNavPhoneConfirmFragment(
                            password = response.value?.password,
                            phone = viewModel.phoneNum)
                    findNavController().navigate(action)
                }
            }.exhaustive

        }
    }

}




