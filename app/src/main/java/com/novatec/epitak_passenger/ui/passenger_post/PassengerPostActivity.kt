package com.novatec.epitak_passenger.ui.passenger_post

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.core.enums.EPostStatus
import com.novatec.epitak_passenger.core.enums.EPostType
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.remote.model.OfferDTO
import com.novatec.epitak_passenger.ui.BaseActivity
import com.novatec.epitak_passenger.ui.addpost.AddPostActivity
import com.novatec.epitak_passenger.ui.dialogs.ARG_IMG
import com.novatec.epitak_passenger.ui.dialogs.ImagePreviewDialog
import com.novatec.epitak_passenger.ui.interfaces.IOnOfferActionListener
import com.novatec.epitak_passenger.ui.viewgroups.ItemOffer
import com.novatec.epitak_passenger.util.*
import com.novatec.epitak_passenger.viewobjects.PassengerPostViewObj
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_passenger_post.*
import kotlinx.android.synthetic.main.view_directions.*
import splitties.experimental.ExperimentalSplittiesApi
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@ExperimentalSplittiesApi
class PassengerPostActivity : BaseActivity(), IOnOfferActionListener {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
        const val REQ_POST_MANIPULATED: Int = 89
    }

    var post: PassengerPost? = null
    var postId: Long = 0
    private val viewModel: PassengerPostViewModel by viewModels()
    private val offersAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_post)
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        setupActionBar()
        rvOffers.adapter = offersAdapter

        viewModel.getPostById(postId)



        attachListeners()
        subscribes()
    }

    private fun subscribes() {

        viewModel.postData.observe(this) {
            post = it ?: return@observe
            showPostData()
        }


        viewModel.offerActionLoading.observe(this) {
            progressOfferAction.visibility = if (it ?: return@observe) View.VISIBLE else View.GONE
        }

        viewModel.offerActionResp.observe(this) {
            refreshAll()
        }

        viewModel.isLoading.observe(this) {
            val value = it ?: return@observe
            swipeRefreshLayout.isRefreshing = value
        }

        viewModel.errorMessage.observe(this) {
            if (it.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
                llOffersContainer.visibility = View.VISIBLE
            } else {
                llOffersContainer.visibility = View.GONE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = it
            }
        }


        viewModel.offerActionError.observe(this) {
            Snackbar.make(swipeRefreshLayout, it ?: return@observe, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.deletePostReponse.observe(this) {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    Snackbar.make(
                        swipeRefreshLayout,
                        response.message,
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(
                        swipeRefreshLayout,
                        response.err.localizedMessage.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultWrapper.Success -> {
                    setResult(RESULT_OK)
                    finish()
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        }

        viewModel.finishPostResponse.observe(this) {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    Snackbar.make(
                        swipeRefreshLayout,
                        response.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(
                        swipeRefreshLayout,
                        response.err.localizedMessage.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultWrapper.Success -> {
                    setResult(RESULT_OK)
                    finish()

                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        }


    }

    private fun populateOffers(value: List<OfferDTO>) {
        offersAdapter.clear()

        offersAdapter.addAll(value.map {
            ItemOffer(it, this@PassengerPostActivity)
        })


    }

    private fun showPostData() {
        post?.let { postNonNull ->

            if (postNonNull.postType == EPostType.PASSENGER_PARCEL) {
                lblPrice.text = getString(R.string.price)
                llParcel.isVisible = true
                imageContainer.isVisible = true
                llSeatsContainer.isVisible = false
                lblPassengersCount.isVisible = false

                postNonNull.imageList.forEach { photo ->
                    parcelImage.loadRounded(photo.link!!, 10)

                    imageContainer.setOnClickListener {
                        ImagePreviewDialog().apply {
                            arguments = Bundle().apply { putString(ARG_IMG, photo.link) }
                        }.show(supportFragmentManager, "")
                    }
                }
            } else {

                lblPrice.text = getString(R.string.price_for_one)
                llParcel.isVisible = false
                imageContainer.isVisible = false
                llSeatsContainer.isVisible = true
                lblPassengersCount.isVisible = true

                llSeatsContainer.removeAllViews()
                for (i in 0 until postNonNull.seat) {
                    val seat = ImageView(this)
                    seat.layoutParams =
                        LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    seat.setImageResource(R.drawable.ic_round_emoji_people_24)
                    llSeatsContainer.addView(seat)
                }
            }

            cancel.isVisible =
                postNonNull.postStatus == EPostStatus.CREATED || postNonNull.postStatus == EPostStatus.WAITING_FOR_START

            when (postNonNull.postStatus) {
                EPostStatus.WAITING_FOR_START -> {
                    llOffersContainer.isVisible = false
                }
                EPostStatus.START -> {
                    llOffersContainer.isVisible = false
                }
                EPostStatus.CANCELED -> {
                    llOffersContainer.isVisible = false
                }
                EPostStatus.FINISHED -> {
                    llOffersContainer.isVisible = false
                }
                EPostStatus.REJECTED -> {
                    llOffersContainer.isVisible = false
                }
                EPostStatus.CREATED -> {
                    llOffersContainer.isVisible = true
                    if (postNonNull.postType == EPostType.PASSENGER_PARCEL) {
                        viewModel.getParcelOffersForPost(postId)
                        viewModel.parcelOffers.observe(this) {
                            when (it) {
                                is ErrorWrapper.ResponseError -> {
                                    progressOfferAction.isVisible = false
                                }
                                is ErrorWrapper.SystemError -> {
                                    progressOfferAction.isVisible = false
                                }
                                ResultWrapper.InProgress -> {
                                    progressOfferAction.isVisible = true
                                }
                                is ResultWrapper.Success -> {
                                    progressOfferAction.isVisible = false
                                    populateOffers(it.value)
                                }
                            }
                            rvOffers.requestLayout()
                        }
                    } else {
                        viewModel.getOffersForPost(postId)
                        viewModel.postOffers.observe(this) {
                            when (it) {
                                is ErrorWrapper.ResponseError -> {
                                    progressOfferAction.isVisible = false
                                }
                                is ErrorWrapper.SystemError -> {
                                    progressOfferAction.isVisible = false
                                }
                                ResultWrapper.InProgress -> {
                                    progressOfferAction.isVisible = true
                                }
                                is ResultWrapper.Success -> {
                                    progressOfferAction.isVisible = false
                                    populateOffers(it.value)
                                }
                            }
                            rvOffers.requestLayout()
                        }
                    }
                }
                EPostStatus.SYSTEM_REJECTED -> {
                    llOffersContainer.isVisible = false
                }
            }

            edit.isVisible = postNonNull.postStatus == EPostStatus.CREATED
            done.isVisible = postNonNull.postStatus == EPostStatus.START

            time.text = PostUtils.timeFromDayParts(
                postNonNull.timeFirstPart,
                postNonNull.timeSecondPart,
                postNonNull.timeThirdPart,
                postNonNull.timeFourthPart
            )
            date.text = DateFormat.format(
                "dd MMMM",
                SimpleDateFormat("dd.MM.yyyy").parse(postNonNull.departureDate)
            )
                .toString()



            if (postNonNull.from.name == null && postNonNull.from.districtName == null) {
                fromDistrict.isVisible = false
                from.text = postNonNull.from.regionName
            } else {
                fromDistrict.isVisible = true
                fromDistrict.text = postNonNull.from.regionName ?: postNonNull.from.name
                from.text = postNonNull.from.districtName
            }

            if (postNonNull.to.name == null && postNonNull.to.districtName == null) {
                toDistrict.isVisible = false
                to.text = postNonNull.to.regionName
            } else {
                toDistrict.isVisible = true
                toDistrict.text = postNonNull.to.regionName ?: postNonNull.to.name
                to.text = postNonNull.to.districtName
            }
            price.text =
                DecimalFormat("#,###").format(postNonNull.price) + " " + getString(R.string.sum)

            if (postNonNull.remark.isNullOrBlank()) {
                note.visibility = View.GONE
            } else {
                note.visibility = View.VISIBLE
                note.text = postNonNull.remark
            }

            if (post!!.driverPost != null) {
                lblMyDriver.text = getString(R.string.your_driver)
                cardDriver.visibility = View.VISIBLE
            } else {
                lblMyDriver.text = getString(R.string.no_driver_assigned_yet)
                cardDriver.visibility = View.GONE
            }
            if (post!!.postStatus == EPostStatus.CREATED) viewModel.getOffersForPost(postId)

            postNonNull.offer?.let {
                price.paintFlags = price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvOfferingPrice.text =
                    DecimalFormat("#,###").format(it.price) + " " + getString(R.string.sum)

            }

            postNonNull.driverPost?.let { driver ->

                tvDriverName.text = driver.profile?.name + " " + driver.profile?.surname

                driver.car?.image?.link?.let { photo ->
                    ivCarPhoto.loadRounded(photo, 10)
                    ivCarPhoto.setOnClickListener {
                        ImagePreviewDialog().apply {
                            arguments = Bundle().apply { putString(ARG_IMG, photo) }
                        }.show(supportFragmentManager, "")
                    }
                }


                driver.profile?.let { driverProfile ->
                    if (driverProfile.rating == null || driverProfile.rating == 0.0F) {
                        ratingBarDriver.isVisible = false
                        ratingBarDriver.text = ""
                    } else {
                        ratingBarDriver.isVisible = true
                        ratingBarDriver.text = driverProfile.rating.toString()
                    }
                    driverProfile.image?.link?.let { avatarLink ->
                        ivDriverAvatar.loadRound(avatarLink)
                    } ?: run {
                        ivDriverAvatar.setImageResource(R.drawable.ic_baseline_account_circle_24)
                    }
                }

                driver.car?.let { car ->
                    carModel.text = car.carModel?.name
                    plateNumber.text = car.carNumber
                }
                fabCallDriver.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:+${driver.profile!!.phoneNum}")
                    startActivity(intent)
                }
            }
        }

    }

    private fun attachListeners() {

        swipeRefreshLayout.setOnRefreshListener {
            refreshAll()
        }

        done.setOnClickListener {
            DialogFinishPost().show(supportFragmentManager, "")
        }

        cancel.setOnClickListener {
            DialogDeletePost().show(supportFragmentManager, "")
        }

        edit.setOnClickListener {
            startActivityForResult(Intent(this, AddPostActivity::class.java).apply {
                putExtra(
                    Constants.TXT_PASSENGER_POST,
                    PassengerPostViewObj.fromPassengerPost(post!!)
                )
            }, REQ_POST_MANIPULATED)
        }
    }


    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.post, postId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun finishPost() = viewModel.finishPost(post!!.id.toString())
    fun deletePost() = viewModel.deletePost(post!!.id.toString())
    fun acceptOffer(offer: OfferDTO) = viewModel.acceptOffer(offer.id)
    fun cancelOffer(offer: OfferDTO) = viewModel.cancelOffer(offer.id)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQ_POST_MANIPULATED) {
            refreshAll()
        }
    }

    private fun refreshAll() {
        viewModel.getPostById(postId)
        viewModel.getOffersForPost(postId)
    }

    override fun onCancelClick(offer: OfferDTO) {
        val dialog = DialogCancelOffer()
        dialog.arguments = Bundle().apply { putParcelable(ARG_OFFER, offer) }
        dialog.show(supportFragmentManager, "")
    }

    override fun onAcceptClick(offer: OfferDTO) {
        val dialog = DialogAcceptOffer()
        dialog.arguments = Bundle().apply { putParcelable(ARG_OFFER, offer) }
        dialog.show(supportFragmentManager, "")
    }

    override fun onPhoneCallClick(offer: OfferDTO) {

    }

    override fun onShowCarImage(imageUrl: String) {
        ImagePreviewDialog().apply {
            arguments = Bundle().apply { putString(ARG_IMG, imageUrl) }
        }.show(supportFragmentManager, "")
    }
}

