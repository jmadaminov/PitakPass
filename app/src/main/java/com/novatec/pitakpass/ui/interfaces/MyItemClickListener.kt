package com.novatec.pitakpass.ui.interfaces

import android.view.View

interface MyItemClickListener {
    fun onClick(pos: Int) {}
    fun onClick(pos: Int, view: View) {}
}