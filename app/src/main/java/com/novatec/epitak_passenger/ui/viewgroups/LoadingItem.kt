package com.novatec.epitak_passenger.ui.viewgroups

import com.novatec.epitak_passenger.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class LoadingItem : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    override fun getLayout()= R.layout.item_loading

}
