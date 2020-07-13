package com.badcompany.pitakpass.di.addcar

import com.badcompany.pitakpass.data.CarRepositoryImpl
import com.badcompany.pitakpass.data.mapper.CarColorMapper
import com.badcompany.pitakpass.data.mapper.CarMapper
import com.badcompany.pitakpass.data.mapper.CarModelMapper
import com.badcompany.pitakpass.data.repository.CarRemote
import com.badcompany.pitakpass.data.source.CarDataStoreFactory
import com.badcompany.pitakpass.data.source.CarRemoteDataStore
import com.badcompany.pitakpass.domain.repository.CarRepository
import com.badcompany.pitakpass.domain.usecases.GetCarColors
import com.badcompany.pitakpass.domain.usecases.GetCarModels
import com.badcompany.pitakpass.domain.usecases.SaveCar
import com.badcompany.pitakpass.domain.usecases.SetDefaultCar
import com.badcompany.pitakpass.di.main.MainScope
import com.badcompany.pitakpass.remote.ApiService
import com.badcompany.pitakpass.remote.CarRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AddCarModule {




}