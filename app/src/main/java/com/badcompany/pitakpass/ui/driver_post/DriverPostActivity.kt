package com.badcompany.pitakpass.ui.driver_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.ui.driver_post.join_a_ride.ARG_DRIVER_POST
import com.badcompany.pitakpass.ui.driver_post.join_a_ride.DialogJoinARideFragment
import com.badcompany.pitakpass.util.loadCircleImageUrl
import com.badcompany.pitakpass.util.loadImageUrl
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj
import kotlinx.android.synthetic.main.activity_driver_post.*
import kotlinx.android.synthetic.main.item_driver_post.view.*
import java.text.DecimalFormat

class DriverPostActivity : BaseActivity() {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
    }

    private val viewModel: DriverPostViewModel by viewModels()
    private lateinit var driverPost: DriverPostViewObj

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_post)
        driverPost = intent.getParcelableExtra(ARG_DRIVER_POST)!!
        setupActionBar()

        attachListeners()
        subscribeObservers()
        showPostData(driverPost)
    }

    private fun subscribeObservers() {
        viewModel.isLoading.observe(this, {
            swipeRefreshLayout.isRefreshing = it ?: return@observe
        })

        viewModel.postData.observe(this) {
            val result = it ?: return@observe
            showPostData(DriverPostViewObj.mapFromDriverPostModel(result))
        }

    }

    private fun showPostData(post: DriverPostViewObj) {

        seats.text = post.seat.toString()
        date.text = post.departureDate
        from.text = post.from.regionName
        to.text = post.to.regionName
        price.text =
            DecimalFormat("#,###").format(post.price) + " " + getString(R.string.sum)

        post.remark?.also {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } ?: run {
            note.visibility = View.GONE
        }



        post.car?.image?.link?.let {
            ivCarPhoto.loadImageUrl(it)
        }

        post.profileDTO?.image?.link?.let {
            ivDriver.loadCircleImageUrl(it)
        }

        post.car?.let {
            var hasAC = ""

            it.airConditioner?.let {
                if (it) hasAC = ", " + getString(R.string.air_conditioner)
            }

            tvCarInfo.text = it.carModel?.name + ", " +
                    it.carYear.toString() + ", " +
                    /*it.carColor?.name + ", "+*/
                    it.carNumber + ", " +
                    it.fuelType +
                    hasAC

        }

        post.profileDTO?.let {
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


}
