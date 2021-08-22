package com.novatec.epitak_passenger.ui.addpost

import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.CalendarView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.novatec.epitak_passenger.R
import com.novatec.epitak_passenger.core.enums.EPostStatus
import com.novatec.epitak_passenger.core.enums.EPostType
import com.novatec.epitak_passenger.domain.model.PassengerPost
import com.novatec.epitak_passenger.domain.model.Place
import com.novatec.epitak_passenger.ui.BaseActivity
import com.novatec.epitak_passenger.ui.bsd_destination.ARG_IS_FROM
import com.novatec.epitak_passenger.ui.bsd_destination.DestinationBSD
import com.novatec.epitak_passenger.ui.bsd_destination.REQ_DESTINATION
import com.novatec.epitak_passenger.ui.bsd_destination.RESULT_PLACE
import com.novatec.epitak_passenger.util.*
import com.novatec.epitak_passenger.viewobjects.PassengerPostViewObj
import com.novatec.epitak_passenger.viewobjects.PlaceViewObj
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi
import java.util.*


class AddPostActivity : BaseActivity() {

    private lateinit var adapter: PkgOrPassengerPagerAdapter
    private val viewModel: AddPostViewModel by viewModels()
    private lateinit var balloon: Balloon

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        setup()
        subscribeObservers()
        attachListeners()


    }

    override fun onResume() {
        super.onResume()
        checkIfEditing(intent.getParcelableExtra(Constants.TXT_PASSENGER_POST))

    }

    private fun checkIfEditing(editingPost: PassengerPostViewObj?) {
        if (editingPost != null) {
            postCreate.text = getString(R.string.update)
            supportActionBar?.title = getString(R.string.edit)
            viewModel.id = editingPost.id

            viewModel.isEditing = true
            viewModel.price = editingPost.price
            viewModel.seat = editingPost.seat
            if (editingPost.imageList.isNotEmpty()) {
                viewModel.pkgPhotoBody = editingPost.imageList[0].toImageBody()
            }
            viewModel.placeFrom = Place(
                editingPost.from.districtId,
                editingPost.from.regionId,
                editingPost.from.lat,
                editingPost.from.lon,
                editingPost.from.regionName,
                editingPost.from.name
            )

            viewModel.placeTo = Place(
                editingPost.to.districtId,
                editingPost.to.regionId,
                editingPost.to.lat,
                editingPost.to.lon,
                editingPost.to.regionName,
                editingPost.to.name
            )

            viewModel.timeFirstPart = editingPost.timeFirstPart
            viewModel.timeSecondPart = editingPost.timeSecondPart
            viewModel.timeThirdPart = editingPost.timeThirdPart
            viewModel.timeFourthPart = editingPost.timeFourthPart
            viewModel.departureDate = editingPost.departureDate
//            viewModel.note = passengerPostViewObj.remark

            viewPager.isUserInputEnabled = false
            if (editingPost.postType == EPostType.PASSENGER_PARCEL) {
                viewPager.currentItem = 1
            } else {
                viewPager.currentItem = 0
            }

            tabLayout.isVisible = false

            noteInput.setText(editingPost.remark)
            priceInput.setText(editingPost.price.toString())
            tvFrom.text = editingPost.from.name ?: editingPost.from.districtName
                    ?: editingPost.from.regionName
            tvTo.text = editingPost.to.name ?: editingPost.to.districtName
                    ?: editingPost.to.regionName
            tvDate.text = DateUtils.getFormattedDate(editingPost.departureDate, this)

            checkFirstPartDay.isChecked = editingPost.timeFirstPart
            checkSecondPartDay.isChecked = editingPost.timeSecondPart
            checkThirdPartDay.isChecked = editingPost.timeThirdPart
            checkFourthPartDay.isChecked = editingPost.timeFourthPart

        }

    }


    private fun attachListeners() {

        priceInput.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) return@doOnTextChanged
            viewModel.price = text.toString().toInt()
            checkFields()
        }

        priceInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                priceInputLayout.clearFocus()
                priceInput.clearFocus()
                priceInput.hideKeyboard()
            }
            true
        }

        checkFirstPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkSecondPartDay.isChecked && !checkThirdPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkFirstPartDay.isChecked = true
            }
            viewModel.timeFirstPart = checkFirstPartDay.isChecked
        }
        checkSecondPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkThirdPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkSecondPartDay.isChecked = true
            }
            viewModel.timeSecondPart = checkSecondPartDay.isChecked
        }
        checkThirdPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkSecondPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkThirdPartDay.isChecked = true
            }
            viewModel.timeThirdPart = checkThirdPartDay.isChecked
        }
        checkFourthPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkSecondPartDay.isChecked && !checkThirdPartDay.isChecked) {
                checkFourthPartDay.isChecked = true
            }
            viewModel.timeFourthPart = checkFourthPartDay.isChecked
        }


        tvDate.setOnClickListener {
            balloon.show(tvDate)
        }

        tvFrom.setOnClickListener {
            DestinationBSD().also {
                it.arguments = Bundle().apply { putBoolean(ARG_IS_FROM, true) }
                this.supportFragmentManager.setFragmentResultListener(
                    REQ_DESTINATION,
                    this
                ) { key, bundle ->
                    viewModel.placeFrom =
                        bundle.getParcelable<PlaceViewObj>(RESULT_PLACE)!!.toPlace()
                    tvFrom.text = viewModel.placeFrom!!.name
                    checkFields()
                }
            }.show(supportFragmentManager, "")
        }

        tvTo.setOnClickListener {
            DestinationBSD().also {
                it.arguments = Bundle().apply { putBoolean(ARG_IS_FROM, false) }
                this.supportFragmentManager.setFragmentResultListener(
                    REQ_DESTINATION,
                    this
                ) { key, bundle ->
                    viewModel.placeTo =
                        bundle.getParcelable<PlaceViewObj>(RESULT_PLACE)!!.toPlace()
                    tvTo.text = viewModel.placeTo!!.name
                    checkFields()
                }
            }.show(supportFragmentManager, "")
        }

        postCreate.setOnClickListener {
            viewModel.createPassengerPost(
                PassengerPost(
                    viewModel.id,
                    viewModel.placeFrom!!,
                    viewModel.placeTo!!,
                    viewModel.price!!,
                    viewModel.departureDate,
                    null,
                    null,
                    null,
                    viewModel.timeFirstPart,
                    viewModel.timeSecondPart,
                    viewModel.timeThirdPart,
                    viewModel.timeFourthPart,
                    null,
                    null,
                    if (noteInput.text.isNullOrBlank()) null else noteInput.text.toString(),
                    EPostStatus.CREATED,
                    if (viewPager.currentItem == 0) viewModel.seat else 0,
                    0,
                    null,
                    if (viewPager.currentItem == 1) listOf(viewModel.pkgPhotoBody!!) else listOf(),
                    if (viewPager.currentItem == 0) EPostType.PASSENGER_SM else EPostType.PASSENGER_PARCEL
                )
            )

        }

    }

    private fun subscribeObservers() {

        viewModel.createResponse.observe(this) {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(scrollView, response.message, Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(
                        scrollView,
                        response.err.localizedMessage!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultWrapper.Success -> {
                    postCreate.stopAnimation()
                    setResult(RESULT_OK)
                    finish()
                }
                ResultWrapper.InProgress -> {
                    postCreate.startAnimation()
                }
            }

        }
    }


    private fun setup() {

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_post)

        setupTabs()

        balloon = Balloon.Builder(this)
            .setLayout(R.layout.layout_calendar)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.LEFT)
            .setArrowPosition(0.5f)
            .setWidthRatio(0.7f)
            .setHeight(360)
            .setCornerRadius(4f)
            .setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            .setBalloonAnimation(BalloonAnimation.CIRCULAR)
            .setLifecycleOwner(this)
            .build()

        val calendar = balloon.getContentView().findViewById<CalendarView>(R.id.calendar)
        val cal = Calendar.getInstance()

        calendar.minDate = cal.timeInMillis

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.setDate(dayOfMonth, month, year)
            val calTemp = Calendar.getInstance()
            calTemp.set(year, month, dayOfMonth)
            tvDate.text = DateUtils.getFormattedDate(calTemp.timeInMillis, this)
            balloon.dismiss()
            checkFields()
        }
        tvDate.text = DateUtils.getFormattedDate(viewModel.calendar.timeInMillis, this)


    }

    private fun setupTabs() {
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        adapter = PkgOrPassengerPagerAdapter(this)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(if (position == 0) R.string.passengers else R.string.parcel)
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                resetTexts(position)
                checkFields()

            }
        })
    }

    private fun resetTexts(position: Int) {
        when (position) {
            0 -> {
                priceInput.setHint(R.string.price_for_one)
            }
            else -> {
                priceInput.setHint(R.string.price_for_parcel)
            }
        }
    }

    fun checkFields() {
        postCreate.isEnabled =
            (viewPager.currentItem == 0 || viewPager.currentItem == 1 && viewModel.pkgPhotoBody != null)
                    && viewModel.departureDate.isNotBlank()
                    && viewModel.price != null
                    && viewModel.placeFrom != null
                    && viewModel.placeTo != null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun seatNumberChanged(seatCount: Int) {
        viewModel.seat = seatCount
    }


}
