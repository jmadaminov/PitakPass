package com.novatec.pitakpass.ui.main.mytrips.historytrips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.novatec.pitakpass.ui.history_post.HistoryPostActivity
import com.novatec.pitakpass.R
import com.novatec.pitakpass.domain.model.PassengerPost
import com.novatec.pitakpass.ui.passenger_post.PassengerPostActivity.Companion.EXTRA_POST_ID
import kotlinx.android.synthetic.main.item_history_post.view.*
import splitties.activities.start
import java.text.DecimalFormat

class HistoryPostAdapter :
    PagingDataAdapter<PassengerPost, HistoryPostAdapter.DriverPostViewHolder>(
        FILTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverPostViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_post, parent, false)
        return DriverPostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: DriverPostViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    class DriverPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: PassengerPost) {
            itemView.apply {

                date.text = post.departureDate
                if (post.from.name == null && post.from.districtName == null) {
                    fromDistrict.isVisible = false
                    from.text = post.from.regionName
                } else {
                    fromDistrict.isVisible = true
                    fromDistrict.text = post.from.regionName ?: post.from.name
                    from.text = post.from.districtName
                }

                if (post.to.name == null && post.to.districtName == null) {
                    toDistrict.isVisible = false
                    to.text = post.to.regionName
                } else {
                    toDistrict.isVisible = true
                    toDistrict.text = post.to.regionName ?: post.to.name
                    to.text = post.to.districtName
                }
                price.text =
                    DecimalFormat("#,###").format(post.price) + " " + itemView.context.getString(R.string.sum)
                seats.text = post.seat.toString()

                post.remark?.also {
                    note.visibility = View.VISIBLE
                    note.text = post.remark
                } ?: run {
                    note.visibility = View.GONE
                }
                cardHistoryItem.setOnClickListener {
                    context.start<HistoryPostActivity> {
                        putExtra(EXTRA_POST_ID, post.id)
                    }
                }


            }
        }
    }

    companion object {

        private val FILTER_COMPARATOR = object : DiffUtil.ItemCallback<PassengerPost>() {
            override fun areItemsTheSame(oldItem: PassengerPost, newItem: PassengerPost) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PassengerPost, newItem: PassengerPost) =
                oldItem == newItem

        }
    }

}