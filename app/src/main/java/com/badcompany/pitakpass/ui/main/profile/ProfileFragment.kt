package com.badcompany.pitakpass.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.badcompany.pitakpass.R
import com.badcompany.pitakpass.ui.auth.AuthActivity
import com.badcompany.pitakpass.ui.feedback.FeedbackActivity
import com.badcompany.pitakpass.ui.main.MainActivity
import com.badcompany.pitakpass.util.AppPrefs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import splitties.preferences.edit
import javax.inject.Inject

//@FlowPreview
//@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment @Inject constructor() :
    Fragment(R.layout.fragment_profile) {

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val viewModel: ProfileViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
//        setupCarsRecyclerView()
        setupListeners()
        subscribeObservers()
//        viewModel.getCarList(AppPreferences.token)
        setupViews()
    }

    @ExperimentalSplittiesApi
    private fun setupViews() {
        (activity as MainActivity).hideTabLayout()
        cardDriver.setBackgroundResource(R.drawable.stroke_rounded_bottom_corners)
        nameSurname.text = "${AppPrefs.name} ${AppPrefs.surname}"
        phone.text = "+${AppPrefs.phone}"

    }

//    private fun setupCarsRecyclerView() {
//        carsList.layoutManager =
//            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
//        carsList.setHasFixedSize(true)
//        carsList.adapter = adapter
//    }

    private fun subscribeObservers() {
//        viewModel.carsResponse.observe(viewLifecycleOwner, Observer {
//            val response = it ?: return@Observer
//            when (response) {
//                is ErrorWrapper.ResponseError -> {
//                    adapter.clear()
//                    adapter.notifyDataSetChanged()
//                    Snackbar.make(clParent, response.message.toString(), Snackbar.LENGTH_SHORT)
//                        .show()
//                }
//                is ErrorWrapper.SystemError -> {
//                    adapter.clear()
//                    adapter.notifyDataSetChanged()
//                    Snackbar.make(clParent,
//                                  getString(R.string.system_error),
//                                  Snackbar.LENGTH_SHORT)
//                        .show()
//
//                }
//                is ResultWrapper.Success -> {
//                    showCars(response.value)
//                }
//                ResultWrapper.InProgress -> {
//                    adapter.clear()
//                    adapter.add(LoadingItem())
//                }
//            }.exhaustive
//
//        })
//
//        viewModel.deleteCarResponse.observe(viewLifecycleOwner, Observer {
//            val response = it ?: return@Observer
//            when (response) {
//                is ErrorWrapper.ResponseError -> {
//                    Snackbar.make(clParent, response.message.toString(), Snackbar.LENGTH_SHORT)
//                        .show()
//                }
//                is ErrorWrapper.SystemError -> {
//                    Snackbar.make(clParent,
//                                  getString(R.string.system_error),
//                                  Snackbar.LENGTH_SHORT)
//                        .show()
//                }
//                is ResultWrapper.Success -> {
//                    adapter.remove(adapter.getItem(response.value))
//                    adapter.notifyItemRemoved(response.value)
//                    if (adapter.itemCount > 0 && adapter.getItem(adapter.itemCount - 1) is CarItemView) {
//                        adapter.add(ItemAddCar(OnItemClickListener { item, view ->
//                            val intent = Intent(context, AddCarActivity::class.java)
//                            startActivityForResult(intent, CODE_ADD_CAR)
//                        }))
//                        adapter.notifyItemInserted(adapter.itemCount)
//                    } else {
//
//                    }
//                }
//                ResultWrapper.InProgress -> {
//                }
//            }.exhaustive
//
//        })
//
//        viewModel.defaultCarResponse.observe(viewLifecycleOwner, Observer {
//            val response = it ?: return@Observer
//            when (response) {
//                is ErrorWrapper.ResponseError -> {
//                    Snackbar.make(clParent, response.message.toString(), Snackbar.LENGTH_SHORT)
//                        .show()
//                }
//                is ErrorWrapper.SystemError -> {
//                    Snackbar.make(clParent,
//                                  getString(R.string.system_error),
//                                  Snackbar.LENGTH_SHORT)
//                        .show()
//                }
//                is ResultWrapper.Success -> {
//                    (adapter.getItem(response.value) as CarItemView).car.def = true
//                    adapter.notifyItemChanged(response.value)
//                }
//                ResultWrapper.InProgress -> {
//                }
//            }.exhaustive
//
//        })

    }

//    @ExperimentalSplittiesApi
//    private fun showCars(value: List<CarDetails>) {
//        adapter.clear()
//        value.forEach { carDetails ->
//            adapter.add(CarItemView(carDetails, object : MyItemClickListener {
//                override fun onClick(pos: Int, view: View) {
//                    val popUpMenu = PopupMenu(context, view.carAction)
//                    requireActivity().menuInflater.inflate(R.menu.car_item_menu, popUpMenu.menu)
//                    popUpMenu.setOnMenuItemClickListener { menuItem ->
//                        when (menuItem.itemId) {
//                            R.id.delete -> {
//                                viewModel.deleteCar(AppPreferences. carDetails.id!!, pos)
//                            }
//                            R.id.edit -> {
//                                val intent = Intent(context, AddCarActivity::class.java)
//                                val imgList = arrayListOf<ImageViewObj>()
//                                carDetails.imageList?.forEach {
//                                    imgList.add(ImageViewObj(it.id, it.link))
//                                }
//                                val carViewObj = CarViewObj(carDetails.id,
//                                                            IdNameViewObj(carDetails.carModel!!.id),
//                                                            ImageViewObj(carDetails.image!!.id,
//                                                                         carDetails.image!!.link),
//                                                            carDetails.fuelType,
//                                                            CarColorViewObj(carDetails.carColor!!.id),
//                                                            carDetails.carNumber,
//                                                            carDetails.carYear,
//                                                            carDetails.airConditioner,
//                                                            carDetails.def,
//                                                            imgList)
//                                intent.putExtra(TXT_CAR, carViewObj)
//                                startActivityForResult(intent, CODE_ADD_CAR)
//                            }
//                            R.id.setDefault -> {
//                                viewModel.setDefault(AppPreferences. carDetails.id!!, pos)
//                            }
//                            else -> {
//
//                            }
//                        }
//                        true
//                    }
//                    popUpMenu.show()
//                }
//            }))
//        }
//        if (value.size < 4) {
//            adapter.add(ItemAddCar(OnItemClickListener { item, view ->
//                val intent = Intent(context, AddCarActivity::class.java)
//                startActivityForResult(intent, CODE_ADD_CAR)
//            }))
//        }
//        adapter.notifyDataSetChanged()
//    }

    private fun setupListeners() {

        btnFeedback.setOnClickListener {
            start<FeedbackActivity>{}
        }

        //        change_password.setOnClickListener {
//            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
//        }
//
//        logout_button.setOnClickListener {
//            viewModel.logout()
//        }
//
//        subscribeObservers()

//        carNameAndNumber.setOnClickListener {
////            start<AddCarActivity>()
//            val intent = Intent(context, AddCarActivity::class.java)
//            startActivityForResult(intent, CODE_ADD_CAR)
//        }

        signOut.setOnClickListener {
            requireActivity().finish()
            AppPrefs.edit {
                token = ""
                name = ""
                surname = ""
                phone = ""
            }
            start<AuthActivity> {}
        }


    }


    @ExperimentalSplittiesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CODE_ADD_CAR && resultCode == RESULT_OK) {
//            Log.d("ON ACTIVITY RESULT   ", "  $resultCode")
//            viewModel.getCarList(AppPreferences.token)
//        }
    }
}

