package com.novatec.epitak_passenger.ui.main.searchtrip

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.core.enums.EFuelType
import com.novatec.epitak_passenger.core.enums.EPostType
import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.ui.driver_post.DriverPostActivity
import com.novatec.epitak_passenger.ui.driver_post.jump_in.ARG_DRIVER_POST
import com.novatec.epitak_passenger.util.Constants
import com.novatec.epitak_passenger.util.PostUtils.timeFromDayParts
import com.novatec.epitak_passenger.util.exhaustive
import com.novatec.epitak_passenger.util.loadRound
import com.novatec.epitak_passenger.viewobjects.DriverPostViewObj
import kotlinx.android.synthetic.main.item_driver_post.view.*
import splitties.activities.start
import java.text.DecimalFormat
import java.text.SimpleDateFormat


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
                cbTakeParcel.isVisible = post.pkg
                llSeatsContainer.removeAllViews()
                var availableSeats = post.availableSeats
                for (i in 0 until post.seat) {
                    val seat = ImageView(context)
                    seat.layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    seat.setImageResource(R.drawable.ic_round_event_seat_24)
                    if (availableSeats > 0) {
                        seat.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
                        availableSeats--
                    } else {
                        seat.setColorFilter(ContextCompat.getColor(context, R.color.error_red))
                    }
                    llSeatsContainer.addView(seat)
                }

//                seats.text =
//                    (post.seat - post.availableSeats!!).toString() + "/" + post.seat.toString()

                time.text = timeFromDayParts(
                    post.timeFirstPart,
                    post.timeSecondPart,
                    post.timeThirdPart,
                    post.timeFourthPart
                )

                date.text = DateFormat.format(
                    "dd MMMM",
                    SimpleDateFormat("dd.MM.yyyy").parse(post.departureDate)
                )
                    .toString()


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
                    DecimalFormat("#,###").format(post.price) + " " + context.getString(R.string.sum)

//                post.remark?.also {
//                    note.visibility = View.VISIBLE
//                    note.text = post.remark
//                } ?: run {
//                    note.visibility = View.GONE
//                }

                card.setOnClickListener {
                    context.start<DriverPostActivity> {
                        putExtra(ARG_DRIVER_POST, DriverPostViewObj.mapFromDriverPostModel(post))
                    }
                }

                if (post.postType == EPostType.DRIVER_PARCEL) {
                    ivCarPhoto.setImageResource(R.drawable.car_trunk)
                    cbTakeParcel.isVisible = true
                } else {
                    post.car?.carModel?.id?.let { id ->
                        ivCarPhoto.setImageResource(
                            when (id) {
                                Constants.ID_GENTRA_LACETTI -> R.drawable.lacetti
                                Constants.ID_COBALT -> R.drawable.cobalt
                                Constants.ID_SPARK -> R.drawable.spark
                                Constants.ID_MATIZ -> R.drawable.matiz
                                Constants.ID_NEXIA_I -> R.drawable.nexia1
                                Constants.ID_NEXIA_II -> R.drawable.nexia2
                                Constants.ID_NEXIA_III -> R.drawable.nexia3
                                Constants.ID_DAMAS -> R.drawable.damas
                                Constants.ID_MALIBU_I -> R.drawable.malibu1
                                Constants.ID_MALIBU_II -> R.drawable.malibu2
                                Constants.ID_CAPTIVA -> R.drawable.captiva
                                Constants.ID_OTHERS -> R.drawable.nocarphoto
                                else -> R.drawable.nocarphoto
                            }
                        )
                    }
                }

                post.profile?.let { driverProfile ->
                    if (driverProfile.rating == null || driverProfile.rating == 0.0F) {
                        ratingBarDriver.isVisible = false
                        ratingBarDriver.text = ""
                    } else {
                        ratingBarDriver.isVisible = true
                        ratingBarDriver.text = driverProfile.rating.toString()
                    }
                    driverProfile.image?.link?.let { avatarLink ->
                        ivDriver.loadRound(avatarLink)
                    } ?: run {
                        ivDriver.setImageResource(R.drawable.ic_baseline_account_circle_24)
                    }
                }

                post.car?.let {

                    ivAC.isVisible = it.airConditioner == true

                    when (it.fuelType!!) {
                        EFuelType.PROPANE -> {
                            ivFuelType.text = context.getString(R.string.propane)
                        }
                        EFuelType.METHANE -> {
                            ivFuelType.text = context.getString(R.string.methane)
                        }
                        EFuelType.PETROL -> {
                            ivFuelType.text = context.getString(R.string.petrol)
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