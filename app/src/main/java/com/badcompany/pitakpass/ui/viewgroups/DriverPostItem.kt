package com.badcompany.pitakpass.ui.viewgroups

import android.view.View
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.ui.driver_post.DriverPostActivity
import com.badcompany.pitakpass.ui.driver_post.join_a_ride.ARG_DRIVER_POST
import com.badcompany.pitakpass.util.loadCircleImageUrl
import com.badcompany.pitakpass.util.loadImageUrl
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj.Companion.mapFromDriverPostModel
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_driver_post.view.*
import splitties.activities.start

class DriverPostItem(val post: DriverPost) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            date.text = post.departureDate
            from.text = post.from.regionName
            to.text = post.to.regionName
            price.text = context.getString(R.string.price_and_seats_format,
                                           post.price.toString(), post.seat.toString())

            if (!post.remark.isBlank()) {
                note.visibility = View.VISIBLE
                note.text = post.remark
            } else {
                note.visibility = View.GONE
            }

            card.setOnClickListener {
                context.start<DriverPostActivity> {
                    putExtra(ARG_DRIVER_POST, mapFromDriverPostModel(post))
                }
            }

            post.car?.image?.link?.let {
                ivCarPhoto.loadImageUrl(it)
            }

            post.profileDTO?.image?.link?.let {
                ivDriver.loadCircleImageUrl(it)
            }

            post.car?.let {
                tvCarInfo.text = it.carModel?.name + "\n"
                it.carYear.toString() + "\n"
                it.carColor?.name + "\n"
                it.carNumber + "\n"
                it.fuelType
            }

            post.profileDTO?.let {
                tvDriverName.text = it.name + " " + it.surname
            }

        }
    }

    override fun getLayout() = R.layout.item_driver_post
}
