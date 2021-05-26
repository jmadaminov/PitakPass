package com.novatec.epitak_passenger.ui.addpost.passengers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.ui.addpost.AddPostActivity
import kotlinx.android.synthetic.main.fragment_passengers.*

class PassengersFragment : Fragment(R.layout.fragment_passengers) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        number_picker.addOnSeatCountChangeListener {
            it?.let {
                (requireActivity() as? AddPostActivity)?.seatNumberChanged(it)
            }
        }
    }
}