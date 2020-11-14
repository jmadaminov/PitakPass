package com.badcompany.pitakpass.ui.passenger_post

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.domain.model.PassengerPost
import com.badcompany.pitakpass.ui.BaseActivity
import com.badcompany.pitakpass.ui.addpost.AddPostActivity
import com.badcompany.pitakpass.util.Constants
import com.badcompany.pitakpass.util.ErrorWrapper
import com.badcompany.pitakpass.util.ResultWrapper
import com.badcompany.pitakpass.util.exhaustive
import com.badcompany.pitakpass.viewobjects.PassengerPostViewObj
import com.badcompany.pitakpass.viewobjects.PlaceViewObj
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_passenger_post.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi class PassengerPostActivity : BaseActivity() {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
        const val REQ_POST_MANIPULATED: Int = 89
    }

    private lateinit var post: PassengerPost
    var postId: Long = 0
    private val viewModel: PassengerPostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_post)
        postId = intent.getLongExtra(EXTRA_POST_ID, 0)
        setupActionBar()
        val offersAdapter = PostOffersAdapter()

        rvOffers.setHasFixedSize(true)
        rvOffers.adapter = offersAdapter


        viewModel.getPostById(postId)
        viewModel.getOffersForPost(postId)

        viewModel.postOffers.observe(this, {
            val value = it ?: return@observe
            offersAdapter.submitData(lifecycle, value)
        })


        attachListeners()
        subscribes()
    }

    private fun subscribes() {

        viewModel.postData.observe(this, {
            post = it ?: return@observe
            showPostData()
        })


        viewModel.isLoading.observe(this, {
            val value = it ?: return@observe
            swipeRefreshLayout.isRefreshing = value
        })


        viewModel.errorMessage.observe(this, {
            if (it.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
                scrollView.visibility = View.VISIBLE
            } else {
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = it
            }
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
//                    adapter.remove(adapter.getItem(response.value))
//                    adapter.notifyItemRemoved(response.value)
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
//                    adapter.remove(adapter.getItem(response.value))
//                    adapter.notifyItemRemoved(response.value)
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive
        })


    }

    private fun showPostData() {
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

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPostById(postId)
        }

        done.setOnClickListener {
            DialogFinishPost().show(supportFragmentManager, "")
        }

        cancel.setOnClickListener {
            DialogDeletePost().show(supportFragmentManager, "")
//            viewModel.deletePost(post.id.toString())
        }



        edit.setOnClickListener {
            val from = PlaceViewObj(post.from.districtId,
                                    post.from.regionId,
                                    post.from.lat,
                                    post.from.lon,
                                    post.from.regionName,
                                    post.from.name)

            val to = PlaceViewObj(post.to.districtId,
                                  post.to.regionId,
                                  post.to.lat,
                                  post.to.lon,
                                  post.from.regionName,
                                  post.from.name)

            start<AddPostActivity> {

                putExtra(Constants.TXT_PASSENGER_POST,
                         PassengerPostViewObj(from,
                                              to,
                                              post.price,
                                              post.departureDate,
                                              post.timeFirstPart,
                                              post.timeSecondPart,
                                              post.timeThirdPart,
                                              post.timeFourthPart,
                                              null,
                                              null,
                                              post.remark,
                                              post.seat,
                                              post.postType))
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

    fun finishPost() = viewModel.finishPost(post.id.toString())
    fun deletePost() = viewModel.deletePost(post.id.toString())


}