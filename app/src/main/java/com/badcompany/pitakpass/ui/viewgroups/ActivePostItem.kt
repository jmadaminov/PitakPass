package com.badcompany.pitakpass.ui.viewgroups

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.core.enums.EPostStatus
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_active_post.view.*
import java.text.DecimalFormat


class ActivePostItem(var post: PassengerPost,
                     var onClick: () -> Unit) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            date.text = post.departureDate
            val fromLbl = StringBuilder()
            val toLbl = StringBuilder()

            post.from.districtName?.let {
                fromLbl.append(" $it")
            }
            if (fromLbl.isBlank()) post.from.name?.let { fromLbl.append(it) }
            post.from.regionName?.let {
                fromDistrict.isVisible = true
                fromDistrict.text = it
            }

            post.to.districtName?.let { toLbl.append(" $it") }
            if (toLbl.isBlank()) post.to.name?.let { toLbl.append(it) }
            post.to.regionName?.let {
                toDistrict.isVisible = true
                toDistrict.text = it
            }

            from.text = fromLbl
            to.text = toLbl
            price.text = post.price.toString()

            if (post.offerCount > 0) {
                tvOffersCount.visibility = View.VISIBLE
                tvOffersCount.text = post.offerCount.toString()
            }else{
                tvOffersCount.visibility = View.GONE
            }

            if (post.remark.isNullOrBlank()){
                note.visibility = View.GONE
            }else{
                note.visibility = View.VISIBLE
                note.text = post.remark
            }

            price.text =
                DecimalFormat("#,###").format(post.price) + " " + context.getString(R.string.sum)

            val currentStatusStr = when (post.postStatus) {
                EPostStatus.WAITING_FOR_START -> {
                    llStatus.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.colorNavIdle)

                    context.getString(R.string.waiting)
                }
                EPostStatus.START -> {
                    llStatus.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.colorPrimary)

                    context.getString(R.string.on_the_way)
                }
                EPostStatus.CANCELED -> {
                    context.getString(R.string.canceled)
                }
                EPostStatus.FINISHED -> {
                    context.getString(R.string.completed)
                }
                EPostStatus.REJECTED -> {
                    context.getString(R.string.rejected)
                }
                EPostStatus.CREATED -> {
                    llStatus.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.neutralColor)
                    context.getString(R.string.boarding)
                }
                EPostStatus.SYSTEM_REJECTED -> {
                    context.getString(R.string.rejected)
                }
            }

            status.text = currentStatusStr


            if (viewHolder.itemView.findViewById<View>(R.id.progress) != null) {
                cl_parent.removeView(viewHolder.itemView.findViewById(R.id.progress))
            }

            cl_parent.setOnClickListener {
                onClick()
            }
        }
    }

    override fun getLayout() = R.layout.item_active_post
}
