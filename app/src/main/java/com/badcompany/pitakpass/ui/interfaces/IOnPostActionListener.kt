package com.badcompany.pitakpass.ui.interfaces

import android.view.View
import com.badcompany.pitakpass.domain.model.PassengerPost

interface IOnPostActionListener {
    fun onEditClick(post: PassengerPost)
    fun onCancelClick(position: Int, post: PassengerPost, parentView: View)
    fun onDoneClick(position: Int, post: PassengerPost, parentView: View)
}