package com.badcompany.pitakpass.ui.driver_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.core.enums.EFuelType
import com.badcompany.pitakpass.domain.model.DriverPost
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.ui.driver_post.jump_in.ARG_DRIVER_POST
import com.badcompany.pitakpass.ui.driver_post.jump_in.DialogJoinARideFragment
import com.badcompany.pitakpass.util.AppPrefs
import com.badcompany.pitakpass.util.exhaustive
import com.badcompany.pitakpass.util.load
import com.badcompany.pitakpass.util.loadRound
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj
import kotlinx.android.synthetic.main.activity_driver_post.*
import kotlinx.android.synthetic.main.activity_driver_post.date
import kotlinx.android.synthetic.main.activity_driver_post.from
import kotlinx.android.synthetic.main.activity_driver_post.fromDistrict
import kotlinx.android.synthetic.main.activity_driver_post.ivCarPhoto
import kotlinx.android.synthetic.main.activity_driver_post.note
import kotlinx.android.synthetic.main.activity_driver_post.price
import kotlinx.android.synthetic.main.activity_driver_post.ratingBarDriver
import kotlinx.android.synthetic.main.activity_driver_post.seats
import kotlinx.android.synthetic.main.activity_driver_post.to
import kotlinx.android.synthetic.main.activity_driver_post.toDistrict
import kotlinx.android.synthetic.main.activity_driver_post.tvDriverName
import kotlinx.android.synthetic.main.activity_history_post.*
import kotlinx.android.synthetic.main.item_driver_post.view.*
import java.text.DecimalFormat

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


//        showPostData(driverPost)
    }

    private fun subscribeObservers() {
        viewModel.isLoading.observe(this, {
            swipeRefreshLayout.isRefreshing = it ?: return@observe
        })

        viewModel.postData.observe(this) {
            val result = it ?: return@observe
            driverPost = DriverPostViewObj.mapFromDriverPostModel(result)
            showPostData(result)
        }

    }

    private fun showPostData(post: DriverPost) {
        btnJumpIn.isVisible = true
        post.passengerList?.forEach { passenger ->
            if (passenger.profile!!.id == AppPrefs.userId) {
                btnJumpIn.isEnabled = false
                btnJumpIn.text = getString(R.string.you_are_already_in)
                btnJumpIn.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.neutralColor)
                return@forEach
            }
        }

        seats.text = post.seat.toString()
        date.text = post.departureDate
        post.profile?.rating?.let { ratingBarDriver.rating = it }

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

        post.remark?.also {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } ?: run {
            note.visibility = View.GONE
        }



        post.car?.image?.link?.let {
            ivCarPhoto.load(it)
        }

        post.profile?.image?.link?.let {
            ivDriver.loadRound(it)
        } ?: run {
            ivDriver.setImageResource(R.drawable.ic_baseline_account_circle_24)
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
