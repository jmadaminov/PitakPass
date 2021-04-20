package com.novatec.pitakpass.ui.interfaces

import android.view.View
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.remote.model.OfferDTO

interface IOnOfferActionListener {
    fun onCancelClick(offer: OfferDTO)
    fun onAcceptClick(offer: OfferDTO)
    fun onPhoneCallClick(offer: OfferDTO)
}