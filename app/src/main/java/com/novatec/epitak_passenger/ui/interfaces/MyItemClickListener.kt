package com.novatec.epitak_passenger.ui.interfaces

import android.view.View

interface MyItemClickListener {
    fun onClick(pos: Int) {}
    fun onClick(pos: Int, view: View) {}
}