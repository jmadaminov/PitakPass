package com.novatec.epitak_passenger.ui.driver_post

import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.core.enums.EFuelType
import com.novatec.epitak_passenger.domain.model.DriverPost
import com.novatec.epitak_passenger.ui.BaseActivity
import com.novatec.epitak_passenger.ui.bsd_offer_parcel.OfferParcelBSD
import com.novatec.epitak_passenger.ui.dialogs.ARG_IMG
import com.novatec.epitak_passenger.ui.dialogs.ImagePreviewDialog
import com.novatec.epitak_passenger.ui.driver_post.jump_in.ARG_DRIVER_POST
import com.novatec.epitak_passenger.ui.driver_post.jump_in.DialogJoinARideFragment
import com.novatec.epitak_passenger.util.*
import com.novatec.epitak_passenger.viewobjects.DriverPostViewObj
import kotlinx.android.synthetic.main.activity_driver_post.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class DriverPostActivity : BaseActivity() {


    private val viewModel: DriverPostViewModel by viewModels()
    private lateinit var driverPost: DriverPostViewObj

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_post)
        driverPost = intent.getParcelableExtra(ARG_DRIVER_POST)!!
        setupActionBar()

        attachListeners()
        subscribeObservers()


    }

    private fun subscribeObservers() {
        viewModel.isLoading.observe(this) {
            swipeRefreshLayout.isRefreshing = it ?: return@observe
        }

        viewModel.postData.observe(this) {
            val result = it ?: return@observe
            driverPost = DriverPostViewObj.mapFromDriverPostModel(result)
            showPostData(result)
        }

    }

    private fun showPostData(post: DriverPost) {
        btnJumpIn.isVisible = true

        cbTakeParcel.isVisible = post.pkg
        btnOfferParcel.isVisible = post.pkg

        post.passengerList?.forEach { passenger ->
            if (passenger.profile!!.id == AppPrefs.userId) {
                btnJumpIn.isEnabled = false
                btnJumpIn.text = getString(R.string.you_are_already_in)
                btnJumpIn.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.neutralColor)
                return@forEach
            }
        }

        llSeatsContainer.removeAllViews()
        var availableSeats = post.availableSeats
        for (i in 0 until post.seat) {
            val seat = ImageView(this)
            seat.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            seat.setImageResource(R.drawable.ic_round_event_seat_24)
            if (availableSeats > 0) {
                seat.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary))
                availableSeats--
            } else {
                seat.setColorFilter(ContextCompat.getColor(this, R.color.error_red))
            }
            llSeatsContainer.addView(seat)
        }

        time.text = PostUtils.timeFromDayParts(
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


        price.text = DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        if (post.remark.isNullOrBlank()) {
            note.visibility = View.GONE
        } else {
            note.visibility = View.VISIBLE
            note.text = post.remark
        }

        post.car?.image?.link?.let { photo ->
            ivCarPhoto.loadRounded(photo, 10)
            ivCarPhoto.setOnClickListener {
                ImagePreviewDialog().apply {
                    arguments = Bundle().apply { putString(ARG_IMG, photo) }
                }.show(supportFragmentManager, "")
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
            plateNumber.text = it.carNumber
            carModel.text = it.carModel?.name
            ivAC.isVisible = it.airConditioner ?: false

            when (it.fuelType!!) {
                EFuelType.PROPANE -> {
                    ivFuelType.text = getString(R.string.propane)
                }
                EFuelType.METHANE -> {
                    ivFuelType.text = getString(R.string.methane)
                }
                EFuelType.PETROL -> {
                    ivFuelType.text = getString(R.string.petrol)
                }
            }.exhaustive
        }

        post.profile?.let {
            tvDriverName.text = it.name + " " + it.surname
        }


    }

    private fun attachListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPostById(driverPost.id)
        }

        btnJumpIn.setOnClickListener {
            val dialog = DialogJoinARideFragment()
            dialog.arguments = Bundle().apply { putParcelable(ARG_DRIVER_POST, driverPost) }
            dialog.show(supportFragmentManager, "")
        }

        btnOfferParcel.setOnClickListener {
            val dialog = OfferParcelBSD()
            dialog.arguments = Bundle().apply { putParcelable(ARG_DRIVER_POST, driverPost) }
            dialog.show(supportFragmentManager, "")
        }

    }


    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.post, driverPost.id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        refreshPost()
    }

    fun refreshPost() {
        viewModel.getPostById(driverPost.id)
    }
}
