package com.novatec.epitak_passenger.ui.addpost.parcel

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.ui.addpost.AddPostActivity
import com.novatec.epitak_passenger.ui.addpost.AddPostViewModel
import com.novatec.epitak_passenger.util.ErrorWrapper
import com.novatec.epitak_passenger.util.ResultWrapper
import com.novatec.epitak_passenger.util.load
import kotlinx.android.synthetic.main.fragment_parcel.*
import java.io.ByteArrayOutputStream

class ParcelFragment : Fragment(R.layout.fragment_parcel),
    BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate {


    private val viewModel: AddPostViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        viewModel.pkgPhotoBody?.link?.let {
            parcelImage.load(it)
        }
    }

    private fun attachListeners() {


        parcelImage.setOnClickListener {
            val singleSelectionPicker: BSImagePicker =
                BSImagePicker.Builder("com.novatec.epitak_passenger.fileprovider")
                    .setSpanCount(3) //Default: 3. This is the number of columns
                    .setTag("")
                    .build()

            singleSelectionPicker.show(childFragmentManager, "picker")
        }

    }

    private fun subscribeObservers() {

        viewModel.uploadPhotoResp.observe(viewLifecycleOwner) {
            when (val result = it) {
                is ErrorWrapper.ResponseError -> {
                    parcelImage.visibility = View.VISIBLE
                    progressImageAdding.visibility = View.INVISIBLE
                    Snackbar.make(
                        llParent,
                        result.message ?: getString(R.string.system_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ErrorWrapper.SystemError -> {
                    parcelImage.visibility = View.VISIBLE
                    progressImageAdding.visibility = View.INVISIBLE
                    Snackbar.make(llParent, result.err.localizedMessage, Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultWrapper.Success -> {
                    (requireActivity() as? AddPostActivity)?.checkFields()
                    parcelImage.visibility = View.VISIBLE
                    progressImageAdding.visibility = View.INVISIBLE
                    parcelImage.load(result.value.link!!)
                }
                ResultWrapper.InProgress -> {
                    parcelImage.visibility = View.INVISIBLE
                    progressImageAdding.visibility = View.VISIBLE
                }
            }

        }

    }

    override fun onSingleImageSelected(uri: Uri, tag: String?) {
        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
        val out = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out)
        viewModel.uploadParcelImage(out.toByteArray())
    }

    override fun loadImage(imageUri: Uri?, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }


}