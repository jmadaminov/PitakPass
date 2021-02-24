package com.badcompany.pitakpass.ui.history_post

import android.animation.LayoutTransition
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.ui.passenger_post.PassengerPostActivity.Companion.EXTRA_POST_ID
import com.badcompany.pitakpass.util.loadCircleImageUrl
import com.badcompany.pitakpass.util.loadImageUrl
import kotlinx.android.synthetic.main.activity_history_post.*
import java.text.DecimalFormat

class HistoryPostActivity : BaseActivity() {


    val viewModel: HistoryPostViewModel by viewModels()
    var postId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_post)
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        setupActionBar()
        rl_parent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        viewModel.getPostById(postId)
        attachListeners()
        subscribes()
    }

    private fun attachListeners() {

        rbYourRate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            btnSubmitRating.visibility =
                if (viewModel.myRating.value == null || rating != viewModel.myRating.value!!.rating || rating != .0F) View.VISIBLE else View.GONE
        }
    }

    private fun subscribes() {


        viewModel.postData.observe(this, {
            showPostData(it ?: return@observe)
        })

        viewModel.myRating.observe(this, {
            val rating = it ?: return@observe
            rbYourRate.rating = rating.rating!!
            btnSubmitRating.isVisible = false
        })

        viewModel.isPostingRating.observe(this, {
            progressRating.isVisible = it ?: return@observe
            btnSubmitRating.isEnabled = !it
        })

        viewModel.isLoading.observe(this, {
            val value = it ?: return@observe
            progress.isVisible = value
            llContent.isVisible = !value
        })

        viewModel.errorMessage.observe(this, {
            if (it.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
            } else {
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = it
            }
        })
    }


    private fun showPostData(post: PassengerPost) {
        viewModel.getMyRating(post.driverPost!!.profile!!.id.toLong())

        btnSubmitRating.setOnClickListener {
            if (viewModel.myRating.value == null || viewModel.myRating.value!!.rating == .0F) {
                viewModel.postMyRating(post.driverPost.profile!!.id.toLong(), rbYourRate.rating)
            } else {
                viewModel.editMyRating(post.driverPost.profile!!.id.toLong(), rbYourRate.rating)
            }
        }

        date.text = post.departureDate
        from.text = post.from.regionName
        to.text = post.to.regionName
        seats.text = post.seat.toString()
        price.text = DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        post.remark?.also {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } ?: run { note.visibility = View.GONE }



        post.driverPost.price.also {
            tvOfferingPrice.text =
                DecimalFormat("#,###").format(it) + " " + getString(R.string.sum)
        }

        tvDriverName.text = post.driverPost.profile?.name + " " + post.driverPost.profile?.surname

        post.driverPost.car?.image?.link?.let { ivCarPhoto.loadImageUrl(it) }

        post.driverPost.profile?.image?.link?.let { ivDriverAvatar.loadCircleImageUrl(it) }

        post.driverPost.profile?.rating?.let { ratingBarDriver.rating = it }

        post.driverPost.car?.let { car ->
            var hasAC = ""

            car.airConditioner?.let { if (it) hasAC = ", " + getString(R.string.air_conditioner) }

            tvCarInfo.text = car.carModel?.name + ", " +
                    car.carYear.toString() + ", " +
                    car.carColor?.name + ", " +
                    car.carNumber + ", " +
                    car.fuelType +
                    hasAC
        }
        fabCallDriver.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+${post.driverPost.profile!!.phoneNum}")
            startActivity(intent)
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

}
