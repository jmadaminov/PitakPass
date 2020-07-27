package com.badcompany.pitakpass.di.addcar
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.badcompany.pitakpass.di.viewmodels.AddCarViewModelFactory
//import com.badcompany.pitakpass.ui.addcar.AddCarViewModel
//import dagger.Binds
//import dagger.Module
//import dagger.multibindings.IntoMap
//
//@Module
//abstract class AddCarViewModelModule {
//
//    @AddCarScope
//    @Binds
//    abstract fun bindViewModelFactory(factory: AddCarViewModelFactory): ViewModelProvider.Factory
//
//    @AddCarScope
//    @Binds
//    @IntoMap
//    @AddCarViewModelKey(AddCarViewModel::class)
//    abstract fun bindAddCarViewModel(viewModel: AddCarViewModel): ViewModel
//
//
//}