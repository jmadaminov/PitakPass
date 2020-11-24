package com.badcompany.pitakpass.ui.viewgroups

import android.view.View
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.ui.driver_post.DriverPostActivity
import com.badcompany.pitakpass.ui.interfaces.MyItemClickListener
import com.badcompany.pitakpass.ui.passenger_post.PassengerPostActivity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_passenger_post.view.*
import splitties.activities.start

class DriverPostItem(val post: DriverPost) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.date.text = post.departureDate
        viewHolder.itemView.from.text = post.from.regionName
        viewHolder.itemView.to.text = post.to.regionName
        viewHolder.itemView.price.text =
            viewHolder.itemView.context.getString(R.string.price_and_seats_format,
                                                  post.price.toString(), post.seat.toString())
//        viewHolder.itemView.seats.text = post.seat.toString()

        if (!post.remark.isBlank()) {
            viewHolder.itemView.note.visibility = View.VISIBLE
            viewHolder.itemView.note.text = post.remark
        } else {
            viewHolder.itemView.note.visibility = View.GONE
        }

        viewHolder.itemView.card.setOnClickListener {
            viewHolder.itemView.context.start<PassengerPostActivity>() {
                putExtra(DriverPostActivity.EXTRA_POST_ID, post.id)
            }
        }

    }

    override fun getLayout() = R.layout.item_passenger_post
}
