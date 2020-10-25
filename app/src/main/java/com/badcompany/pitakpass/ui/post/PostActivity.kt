package com.badcompany.pitakpass.ui.post

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

class PostActivity : BaseActivity() {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
    }

    var postId: Int = 0
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        setupActionBar()
        postId = intent.getIntExtra(EXTRA_POST_ID, 0)
        viewModel.getPostById(postId)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.postData.observe(this, {
            val post = it ?: return@observe
            showPostData()
        })
    }

    private fun showPostData() {


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
