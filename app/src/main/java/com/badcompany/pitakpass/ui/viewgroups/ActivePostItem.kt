package com.badcompany.pitakpass.ui.viewgroups

import android.view.View
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_active_post.view.*


class ActivePostItem(var post: PassengerPost/*, var onPostActionListener: IOnPostActionListener*/,
                     var onClick: () -> Unit) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            date.text = post.departureDate
            from.text = post.from.regionName
            to.text = post.to.regionName
            price.text = post.price.toString()

            post.remark?.also {
                note.visibility = View.VISIBLE
                note.text = post.remark
            } ?: run {
                note.visibility = View.GONE
            }


            if (viewHolder.itemView.findViewById<View>(R.id.progress) != null) {
                cl_parent.removeView(viewHolder.itemView.findViewById<View>(R.id.progress))
            }

            cl_parent.setOnClickListener {
                onClick()
            }
        }
    }

    override fun getLayout() = R.layout.item_active_post
}
