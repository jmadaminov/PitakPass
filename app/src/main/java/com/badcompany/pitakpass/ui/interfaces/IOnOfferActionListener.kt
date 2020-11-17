package com.badcompany.pitakpass.ui.interfaces

import android.view.View
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.remote.model.OfferDTO

interface IOnOfferActionListener {
    fun onCancelClick(offer: OfferDTO)
    fun onAcceptClick(offer: OfferDTO)
}