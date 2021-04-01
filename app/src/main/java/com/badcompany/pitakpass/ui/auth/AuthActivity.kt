package com.badcompany.pitakpass.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.BaseActivity
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

