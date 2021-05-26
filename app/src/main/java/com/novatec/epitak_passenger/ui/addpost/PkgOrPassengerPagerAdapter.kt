package com.novatec.epitak_passenger.ui.addpost

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.novatec.epitak_passenger.ui.addpost.parcel.ParcelFragment
import com.novatec.epitak_passenger.ui.addpost.passengers.PassengersFragment


class PkgOrPassengerPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> PassengersFragment()
        else -> ParcelFragment()
    }


}