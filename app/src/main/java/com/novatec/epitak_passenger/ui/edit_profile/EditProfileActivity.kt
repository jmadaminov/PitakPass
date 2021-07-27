package com.novatec.epitak_passenger.ui.edit_profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.ui.BaseActivity
import com.novatec.epitak_passenger.util.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class EditProfileActivity : BaseActivity(), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate {


    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        setupViews()
        attachListeners()
        subscribeObservers()
    }


    fun attachListeners() {
        edtName.doOnTextChanged { text, start, before, count -> checkInputs() }

        edtSurname.doOnTextChanged { text, start, before, count -> checkInputs() }

        ivAvatar.setOnClickListener {
            val singleSelectionPicker: BSImagePicker =
                BSImagePicker.Builder("com.novatec.epitak_passenger.fileprovider")
                    .setSpanCount(3) //Default: 3. This is the number of columns
                    .setTag("")
                    .build()

            singleSelectionPicker.show(supportFragmentManager, "picker")
        }

        btnUpdate.setOnClickListener {
            viewModel.updateProfile(
                edtName.text.toString(),
                edtSurname.text.toString()
            )
        }


    }

    private fun checkInputs() {
        btnUpdate.isEnabled = !edtName.text.isNullOrBlank() && !edtSurname.text.isNullOrBlank()

    }

    fun subscribeObservers() {

        viewModel.uploadPhotoResp.observe(this) {
            val result = it
            when (result) {
                is ErrorWrapper.ResponseError -> {
                    ivAvatar.visibility = View.VISIBLE
                    progressAvatar.visibility = View.INVISIBLE
                    Snackbar.make(
                        llParent,
                        result.message ?: getString(R.string.system_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ErrorWrapper.SystemError -> {
                    ivAvatar.visibility = View.VISIBLE
                    progressAvatar.visibility = View.INVISIBLE
                    Snackbar.make(llParent, result.err.localizedMessage, Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultWrapper.Success -> {
                    checkInputs()
                    ivAvatar.visibility = View.VISIBLE
                    progressAvatar.visibility = View.INVISIBLE
                }
                ResultWrapper.InProgress -> {
                    ivAvatar.visibility = View.INVISIBLE
                    progressAvatar.visibility = View.VISIBLE
                }
            }.exhaustive

        }

        viewModel.isUpdating.observe(this) {
            if (it) {
                tvError.visibility = View.INVISIBLE
                btnUpdate.startAnimation()
            } else {
                btnUpdate.revertAnimation()
            }
        }
        viewModel.errorMessage.observe(this) {
            btnUpdate.revertAnimation()
            tvError.isVisible = true
            tvError.text = it
        }
        viewModel.updateSuccess.observe(this) {
            btnUpdate.doneLoadingAnimation(
                Color.GREEN,
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_check_24
                )!!
                    .toBitmap()
            )
            finish()
        }

    }

    fun setupViews() {

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        edtSurname.setText(UserPrefs.surname)
        edtName.setText(UserPrefs.name)
        if (UserPrefs.avatar.isNotBlank()) ivAvatar.loadRound(UserPrefs.avatar)
    }

    override fun onSingleImageSelected(uri: Uri, tag: String?) {
        checkInputs()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val out = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out)
        val decoded = BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))
        ivAvatar.loadRound(decoded)
        viewModel.uploadAvatar(out.toByteArray())
    }

    override fun loadImage(imageUri: Uri?, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}