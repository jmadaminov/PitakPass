package com.novatec.epitak_passenger.ui.viewgroups

import android.view.View
import androidx.core.view.isVisible
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.core.enums.EFuelType
import com.novatec.epitak_passenger.remote.model.OfferDTO
import com.novatec.epitak_passenger.ui.interfaces.IOnOfferActionListener
import com.novatec.epitak_passenger.util.loadRound
import com.novatec.epitak_passenger.util.loadRounded
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_offer.view.*
import java.text.DecimalFormat


class ItemOffer(val offer: OfferDTO, val listener: IOnOfferActionListener) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            if (offer.message.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
            } else {
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = offer.message
            }

            offer.price?.also {
                tvOfferingPrice.text =
                    DecimalFormat("#,###").format(it) + " " + context.getString(R.string.sum)
            } ?: run {
                tvOfferingPrice.text =
                    context.getString(R.string.your_price)
            }

            tvName.text = offer.profile.name + " " + offer.profile.surname

            offer.car?.image?.link?.let { imgUrl ->
                ivCarPhoto.loadRounded(imgUrl, 10)
                ivCarPhoto.setOnClickListener {
                    listener.onShowCarImage(imgUrl)
                }
            }

            offer.profile.image?.link?.let {
                ivAvatar.loadRound(it, R.drawable.ic_baseline_account_circle_24)
            }

            offer.profile.rating?.let { ratingBarDriver.rating = it }

            offer.car?.let {
                tvCarModel.text = it.carModel?.name
                ivAC.isVisible = it.airConditioner!!
                ivFuelType.text = when (it.fuelType!!) {
                    EFuelType.PROPANE -> context.getString(R.string.propane)
                    EFuelType.METHANE -> context.getString(R.string.methane)
                    EFuelType.PETROL -> context.getString(R.string.petrol)
                }
            }
            ivDeny.setOnClickListener { listener.onCancelClick(offer) }
            ivAccept.setOnClickListener { listener.onAcceptClick(offer) }
        }
    }

    override fun getLayout() = R.layout.item_offer

}
