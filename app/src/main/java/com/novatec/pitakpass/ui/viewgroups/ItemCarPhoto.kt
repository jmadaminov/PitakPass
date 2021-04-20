package com.novatec.pitakpass.ui.viewgroups

import com.novatec.pitakpass.domain.model.PhotoBody
import com.novatec.pitakpass.R
import com.novatec.pitakpass.util.load
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_car_photo.view.*

class ItemCarPhoto(val photoBody: PhotoBody, val deleteClickListener: OnItemClickListener) :
    Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.iv_delete.setOnClickListener {
            deleteClickListener.onItemClick(this, it)
        }
        viewHolder.itemView.iv_car_photo.load(photoBody.link!!)
    }

    override fun getLayout() = R.layout.item_car_photo
}
