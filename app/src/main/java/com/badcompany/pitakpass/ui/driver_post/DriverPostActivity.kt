package com.badcompany.pitakpass.ui.driver_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_passenger_post.*
import splitties.activities.start

class DriverPostActivity : BaseActivity() {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
    }

    var postId: Long = 0
    private val viewModel: DriverPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_post)
        setupActionBar()
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        viewModel.getPostById(postId)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.postData.observe(this, {
            val post = it ?: return@observe
            showPostData(post)
        })
    }

    private fun showPostData(post: PassengerPost) {


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

        card.setOnClickListener {
            start<DriverPostActivity>()
        }

    }

    private fun attachListeners() {

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
