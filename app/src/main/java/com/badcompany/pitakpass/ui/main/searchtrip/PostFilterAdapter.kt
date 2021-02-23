package com.badcompany.pitakpass.ui.main.searchtrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.core.enums.EFuelType
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.ui.driver_post.DriverPostActivity
import com.badcompany.pitakpass.ui.driver_post.jump_in.ARG_DRIVER_POST
import com.badcompany.pitakpass.util.exhaustive
import com.badcompany.pitakpass.util.loadCircleImageUrl
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj
import kotlinx.android.synthetic.main.item_driver_post.view.*
import splitties.activities.start
import java.text.DecimalFormat

class PostFilterAdapter :
    PagingDataAdapter<DriverPost, PostFilterAdapter.DriverPostViewHolder>(FILTER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverPostViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_driver_post, parent, false)
        return DriverPostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: DriverPostViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    class DriverPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: DriverPost) {
            itemView.apply {
                seats.text =
                    (post.seat - post.availableSeats!!).toString() + "/" + post.seat.toString()
                date.text = post.departureDate
                from.text = post.from.regionName
                to.text = post.to.regionName
                price.text =
                    DecimalFormat("#,###").format(post.price) + " " + context.getString(R.string.sum)

                post.remark?.also {
                    note.visibility = View.VISIBLE
                    note.text = post.remark
                } ?: run {
                    note.visibility = View.GONE
                }

                card.setOnClickListener {
                    context.start<DriverPostActivity> {
                        putExtra(ARG_DRIVER_POST, DriverPostViewObj.mapFromDriverPostModel(post))
                    }
                }

//                post.car?.image?.link?.let {
//                    ivCarPhoto.loadImageUrl(it)
//                }

                post.profile?.let { driverProfile ->
                    driverProfile.rating?.let { rating ->
                        ratingBarDriver.rating = rating
                    }
                    driverProfile.image?.link?.let { avatarLink ->
                        ivDriver.loadCircleImageUrl(avatarLink)
                    }
                }

                post.car?.let {

                    ivAC.isVisible = it.airConditioner == true

                    when (it.fuelType!!) {
                        EFuelType.PROPANE -> {
                            ivFuelType.setImageResource(R.drawable.ic_propane)
                        }
                        EFuelType.METHANE -> {
                            ivFuelType.setImageResource(R.drawable.ic_methane)
                        }
                        EFuelType.PETROL -> {
                            ivFuelType.setImageResource(R.drawable.ic_gascan)
                        }
                    }.exhaustive

                }

                post.profile?.let {
                    tvDriverName.text = it.name + " " + it.surname
                }

            }
        }
    }

    companion object {

        private val FILTER_COMPARATOR = object : DiffUtil.ItemCallback<DriverPost>() {
            override fun areItemsTheSame(oldItem: DriverPost, newItem: DriverPost) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DriverPost, newItem: DriverPost) =
                oldItem == newItem

        }
    }

}