package com.badcompany.pitakpass.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.main.MainActivity
import com.badcompany.pitakpass.util.AppPrefs
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ContextUtils.setLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_language.*


@AndroidEntryPoint
class DialogLanguage : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_language, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_tick_en!!.visibility = View.INVISIBLE
        iv_tick_ru!!.visibility = View.INVISIBLE
        iv_tick_uzb!!.visibility = View.INVISIBLE



        when (AppPrefs.language) {
            Constants.RU -> iv_tick_ru!!.visibility = View.VISIBLE
            Constants.EN -> iv_tick_en!!.visibility = View.VISIBLE
            Constants.UZ -> iv_tick_uzb!!.visibility = View.VISIBLE
        }


        layout_ru.setOnClickListener {
            setLocale(Constants.RU, requireContext())
            restart()
        }

        layout_en.setOnClickListener {
            setLocale(Constants.EN, requireContext())
            restart()
        }

        layout_uzb.setOnClickListener {
            setLocale(Constants.UZ, requireContext())
            restart()
        }


    }

    fun restart() {
        context?.startActivities(arrayOf(Intent(context, MainActivity::class.java),
                                         Intent(context, SettingsActivity::class.java)))
        requireActivity().finish()
    }

    override fun getTheme() = R.style.Theme_Dialog
}