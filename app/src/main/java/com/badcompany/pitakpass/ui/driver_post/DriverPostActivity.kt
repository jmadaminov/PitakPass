package com.badcompany.pitakpass.ui.driver_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.ui.driver_post.join_a_ride.ARG_DRIVER_POST
import com.badcompany.pitakpass.ui.driver_post.join_a_ride.DialogJoinARideFragment
import com.badcompany.pitakpass.viewobjects.DriverPostViewObj
import kotlinx.android.synthetic.main.activity_driver_post.*

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

    }

    private fun showPostData(post: DriverPostViewObj) {


        date.text = post.departureDate
        from.text = post.from.regionName
        to.text = post.to.regionName
        price.text =
            getString(R.string.price_and_seats_format,
                      post.price.toString(), post.seat.toString())
//        seats.text = post.seat.toString()

        if (!post.remark.isBlank()) {
            note.visibility = View.VISIBLE
            note.text = post.remark
        } else {
            note.visibility = View.GONE
        }


    }

    private fun attachListeners() {
        btnOfferARide.setOnClickListener {
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
