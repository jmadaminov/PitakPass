package com.badcompany.pitakpass.ui.viewgroups

import android.view.View
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.ui.interfaces.IOnPostActionListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_active_post.view.*

class HistoryPostItem(var post: PassengerPost, var onPostActionListener: IOnPostActionListener) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            date.text = post.departureDate
            from.text = post.from.regionName
            to.text = post.to.regionName
            price.text =
                context.getString(R.string.price_and_seats_format,
                                  post.price.toString(), post.seat.toString())

            post.remark?.also {
                note.visibility = View.VISIBLE
                note.text = post.remark
            } ?: run {
                note.visibility = View.GONE
            }

        }
    }

    override fun getLayout() = R.layout.item_history_post
}
