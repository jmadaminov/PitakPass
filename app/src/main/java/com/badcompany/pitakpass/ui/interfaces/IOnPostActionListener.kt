package com.badcompany.pitakpass.ui.interfaces

import android.view.View
import com.badcompany.pitakpass.domain.domainmodel.DriverPost

interface IOnPostActionListener {
    fun onEditClick(post: DriverPost)
    fun onCancelClick(position:Int,post: DriverPost, parentView: View)
    fun onDoneClick(position:Int,post: DriverPost, parentView: View)
}