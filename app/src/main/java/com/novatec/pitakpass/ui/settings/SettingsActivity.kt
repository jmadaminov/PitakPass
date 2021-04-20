package com.novatec.pitakpass.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.novatec.pitakpass.R
import com.novatec.pitakpass.ui.BaseActivity
import com.novatec.pitakpass.util.AppPrefs
import com.novatec.pitakpass.util.Constants
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {


    private val viewModel: SettingsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        when (AppPrefs.language) {
            Constants.EN -> tv_language.text = "English"
            Constants.RU -> tv_language.text = "Русский"
            Constants.UZ -> tv_language.text = "O'zbek tili"
        }

        setupActionbar()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {
        cl_language.setOnClickListener {
            DialogLanguage().show(supportFragmentManager, "")
        }

    }

    private fun setupActionbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}