package com.badcompany.pitakpass.ui.driver_post.join_a_ride

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_offer_a_ride.*

const val ARG_DRIVER_POST = "DRIVER_POST"

@AndroidEntryPoint
class DialogOfferARideFragment : DialogFragment() {

    private lateinit var driverPost: DriverPostViewObj
    val viewModel: JoinARideViewModel by viewModels()

    override fun getTheme() = R.style.Theme_Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        driverPost = requireArguments().getParcelable(ARG_DRIVER_POST)!!
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_offer_a_ride, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()

    }

    private fun subscribeObservers() {

        viewModel.isOffering.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) btnSendOffer.startAnimation() else btnSendOffer.revertAnimation()
        })

        viewModel.hasFinished.observe(viewLifecycleOwner, { hasFinished ->
           if (hasFinished) dismiss()
        })

    }

    private fun attachListeners() {

        edtPrice.doOnTextChanged { text, start, before, count ->
        }

        btnSendOffer.setOnClickListener {
            viewModel.joinARide(driverPost.id,
                                if (edtPrice.text.isNullOrBlank()) null else edtPrice.text.toString()
                                     .toInt(),
                                messageInput.text.toString())
        }
    }
}