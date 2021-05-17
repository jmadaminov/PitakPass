package com.novatec.epitak_passenger.ui.interfaces

import android.view.View
import com.novatec.epitak_passenger.domain.model.PassengerPost

interface IOnPostActionListener {
    fun onEditClick(post: PassengerPost)
    fun onCancelClick(position: Int, post: PassengerPost, parentView: View)
    fun onDoneClick(position: Int, post: PassengerPost, parentView: View)
}