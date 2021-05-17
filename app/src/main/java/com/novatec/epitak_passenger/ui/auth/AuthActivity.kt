package com.novatec.epitak_passenger.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        subscribeObservers()


    }

    private fun subscribeObservers() {

    }



}

