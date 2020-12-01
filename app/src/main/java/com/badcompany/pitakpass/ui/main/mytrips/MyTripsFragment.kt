package com.badcompany.pitakpass.ui.main.mytrips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.main.MainActivity
import com.badcompany.pitakpass.ui.main.mytrips.activetrips.ActiveTripsFragment
import com.badcompany.pitakpass.ui.main.mytrips.historytrips.HistoryTripsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_my_trips.*
import javax.inject.Inject

@AndroidEntryPoint
class MyTripsFragment @Inject constructor() : Fragment(R.layout.fragment_my_trips) {

    private val viewModel: MyTripsViewModel by viewModels()
    private lateinit var mediator: TabLayoutMediator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).showTabLayout()
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager)
        pager.adapter = pagerAdapter
        mediator = TabLayoutMediator(requireActivity().findViewById(R.id.tab_layout),
                                     pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.active)
                else -> getString(R.string.history)
            }
        }
        mediator.attach()
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentStateAdapter(this) {

        lateinit var currentFrag: Fragment
        var activeOrdersFrag = ActiveTripsFragment()
        var historyOrdersFrag = HistoryTripsFragment()

        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            currentFrag = when (position) {
                0 -> activeOrdersFrag
                else -> historyOrdersFrag
            }
            return currentFrag
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        pager.adapter = null
        mediator.detach()
    }
}