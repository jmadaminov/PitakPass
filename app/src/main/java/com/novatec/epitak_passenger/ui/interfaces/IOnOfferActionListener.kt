package com.novatec.epitak_passenger.ui.interfaces

import android.view.View
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.remote.model.OfferDTO

interface IOnOfferActionListener {
    fun onCancelClick(offer: OfferDTO)
    fun onAcceptClick(offer: OfferDTO)
    fun onPhoneCallClick(offer: OfferDTO)
    fun onShowCarImage(imageUrl:String)
}