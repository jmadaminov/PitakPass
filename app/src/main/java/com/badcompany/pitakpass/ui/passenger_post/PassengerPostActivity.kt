package com.badcompany.pitakpass.ui.passenger_post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.remote.model.OfferDTO
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.core.enums.EPostStatus
import com.badcompany.pitakpass.ui.addpost.AddPostActivity
import com.badcompany.pitakpass.ui.interfaces.IOnOfferActionListener
import com.badcompany.pitakpass.util.*
import com.badcompany.pitakpass.viewobjects.PassengerPostViewObj
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_passenger_post.*
import kotlinx.android.synthetic.main.activity_passenger_post.date
import kotlinx.android.synthetic.main.activity_passenger_post.from
import kotlinx.android.synthetic.main.activity_passenger_post.fromDistrict
import kotlinx.android.synthetic.main.activity_passenger_post.ivCarPhoto
import kotlinx.android.synthetic.main.activity_passenger_post.note
import kotlinx.android.synthetic.main.activity_passenger_post.price
import kotlinx.android.synthetic.main.activity_passenger_post.seats
import kotlinx.android.synthetic.main.activity_passenger_post.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_passenger_post.to
import kotlinx.android.synthetic.main.activity_passenger_post.toDistrict
import kotlinx.android.synthetic.main.activity_passenger_post.tvCarInfo
import kotlinx.android.synthetic.main.activity_passenger_post.tvDriverName
import kotlinx.android.synthetic.main.activity_passenger_post.tvMessage
import kotlinx.android.synthetic.main.item_driver_post.view.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import java.text.DecimalFormat

@ExperimentalSplittiesApi class PassengerPostActivity : BaseActivity() {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
        const val REQ_POST_MANIPULATED: Int = 89
    }

    var post: PassengerPost? = null
    var postId: Long = 0
    private val viewModel: PassengerPostViewModel by viewModels()

    lateinit var offersAdapter: PostOffersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_post)
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        setupActionBar()
        offersAdapter = PostOffersAdapter(object : IOnOfferActionListener {
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

        })

        rvOffers.setHasFixedSize(true)
        rvOffers.adapter = offersAdapter

        viewModel.getPostById(postId)
        viewModel.getOffersForPost(postId)


        attachListeners()
        subscribes()
    }

    private fun subscribes() {

        viewModel.postData.observe(this, {
            post = it ?: return@observe
            showPostData()
        })


        viewModel.offerActionLoading.observe(this, {
            progressOfferAction.visibility = if (it ?: return@observe) View.VISIBLE else View.GONE
        })

        viewModel.offerActionResp.observe(this, {
            viewModel.getPostById(postId)
            viewModel.getOffersForPost(postId)
            offersAdapter.refresh()
        })

        viewModel.isLoading.observe(this, {
            val value = it ?: return@observe
            swipeRefreshLayout.isRefreshing = value
        })

        viewModel.errorMessage.observe(this, {
            if (it.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
                llOffersContainer.visibility = View.VISIBLE
            } else {
                llOffersContainer.visibility = View.GONE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = it
            }
        })

        viewModel.offerActionError.observe(this, {
            Snackbar.make(swipeRefreshLayout, it ?: return@observe, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.deletePostReponse.observe(this, {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    setResult(RESULT_OK)
                    finish()
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        })

        viewModel.finishPostResponse.observe(this, {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.message!!,
                                  Snackbar.LENGTH_SHORT).show()

                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(swipeRefreshLayout,
                                  response.err.localizedMessage.toString(),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    setResult(RESULT_OK)
                    finish()

                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        })


    }

    private fun showPostData() {
        post?.let { postNonNull ->
            cancel.isVisible =
                postNonNull.postStatus == EPostStatus.CREATED || postNonNull.postStatus == EPostStatus.WAITING_FOR_START

            if (postNonNull.postStatus == EPostStatus.CREATED) {
                viewModel.postOffers?.observe(this, {
                    val value = it ?: return@observe
                    offersAdapter.submitData(lifecycle, value)
                    rvOffers.requestLayout()
                })
            }
            edit.isVisible = postNonNull.postStatus == EPostStatus.CREATED
            done.isVisible = postNonNull.postStatus == EPostStatus.START

            date.text = postNonNull.departureDate
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
            seats.text = postNonNull.seat.toString()

            if (postNonNull.remark.isNullOrBlank()){
                note.visibility = View.GONE
            }else{
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

            postNonNull.driverPost?.let { driver ->

                driver.price.also {
                    tvOfferingPrice.text =
                        DecimalFormat("#,###").format(it) + " " + getString(R.string.sum)
                }

                tvDriverName.text = driver.profile?.name + " " + driver.profile?.surname

                driver.car?.image?.link?.let {
                    ivCarPhoto.load(it)
                }

                driver.profile?.image?.link?.let {
                    ivDriverAvatar.loadRound(it)
                } ?: run{
                    ivDriverAvatar.setImageResource(R.drawable.ic_baseline_account_circle_24)
                }

                driver.profile?.rating?.let {
                    ratingBarDriver.rating = it
                }

                driver.car?.let { car ->
                    var hasAC = ""

                    car.airConditioner?.let {
                        if (it) hasAC = ", " + getString(R.string.air_conditioner)
                    }

                    tvCarInfo.text = car.carModel?.name + ", " +
                            car.carYear.toString() + ", " +
                            car.carColor?.name + ", " +
                            car.carNumber + ", " +
                            car.fuelType +
                            hasAC

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
            viewModel.getPostById(postId)
            viewModel.getOffersForPost(postId)
            offersAdapter.refresh()
        }

        done.setOnClickListener {
            DialogFinishPost().show(supportFragmentManager, "")
        }

        cancel.setOnClickListener {
            DialogDeletePost().show(supportFragmentManager, "")
        }

        edit.setOnClickListener {
            start<AddPostActivity> {
                putExtra(Constants.TXT_PASSENGER_POST,
                         PassengerPostViewObj.fromPassengerPost(post!!))
            }
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


}
