package com.badcompany.pitakpass.di.main

import com.badcompany.pitakpass.data.DriverPostRepositoryImpl
import com.badcompany.pitakpass.data.PassengerPostRepositoryImpl
import com.badcompany.pitakpass.data.mapper.DriverPostMapper
import com.badcompany.pitakpass.data.mapper.FilterMapper
import com.badcompany.pitakpass.data.mapper.PassengerPostMapper
import com.badcompany.pitakpass.data.mapper.PlaceMapper
import com.badcompany.pitakpass.data.repository.DriverPostRemote
import com.badcompany.pitakpass.data.repository.PassengerPostDataStore
import com.badcompany.pitakpass.data.repository.PassengerPostRemote
import com.badcompany.pitakpass.data.repository.PlaceRemote
import com.badcompany.pitakpass.data.source.*
import com.badcompany.pitakpass.domain.repository.DriverPostRepository
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.domain.usecases.*
import com.badcompany.pitakpass.remote.ApiService
import com.badcompany.pitakpass.remote.DriverPostRemoteImpl
import com.badcompany.pitakpass.remote.PassengerPostRemoteImpl
import com.badcompany.pitakpass.remote.PlaceRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object MainModule {


    @MainScope
    @Provides
    @JvmStatic
    fun provideGetPassengerPostWithFilter(passengerPostRepository: PassengerPostRepository): GetPassengerPostWithFilter {
        return GetPassengerPostWithFilter(passengerPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun providePassengerPostRepository(factory: PassengerPostDataStoreFactory,
                                       driverPostMapper: PassengerPostMapper,
                                       filterMapper: FilterMapper): PassengerPostRepository {
        return PassengerPostRepositoryImpl(factory, driverPostMapper, filterMapper)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePassengerPostDataStoreFactory(postDataStore: PassengerPostDataStore): PassengerPostDataStoreFactory {
        return PassengerPostDataStoreFactory(postDataStore)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePassengerPostDataStore(passengerPostRemote: PassengerPostRemote): PassengerPostDataStore {
        return PassengerPostRemoteDataStore(passengerPostRemote)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePassengerPostRemote(apiService: ApiService,
                                   postMapper: com.badcompany.pitakpass.remote.mapper.PassengerPostMapper,
                                   filterMapper: com.badcompany.pitakpass.remote.mapper.FilterMapper): PassengerPostRemote {
        return PassengerPostRemoteImpl(apiService, postMapper, filterMapper)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemotePassengerPostMapper(): com.badcompany.pitakpass.remote.mapper.PassengerPostMapper {
        return com.badcompany.pitakpass.remote.mapper.PassengerPostMapper()
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemoteFilterMapper(): com.badcompany.pitakpass.remote.mapper.FilterMapper {
        return com.badcompany.pitakpass.remote.mapper.FilterMapper()
    }


    @MainScope
    @Provides
    @JvmStatic
    fun provideGetActiveDriverPost(driverPostRepository: DriverPostRepository): GetActiveDriverPost {
        return GetActiveDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideDeleteDriverPost(driverPostRepository: DriverPostRepository): DeleteDriverPost {
        return DeleteDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideFinishDriverPost(driverPostRepository: DriverPostRepository): FinishDriverPost {
        return FinishDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideGetHistoryDriverPost(driverPostRepository: DriverPostRepository): GetHistoryDriverPost {
        return GetHistoryDriverPost(driverPostRepository)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideDriverPostRepository(factory: DriverPostDataStoreFactory,
                                    driverPostMapper: DriverPostMapper): DriverPostRepository {
        return DriverPostRepositoryImpl(factory, driverPostMapper)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceMapper(): PlaceMapper {
        return PlaceMapper()
    }

    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostMapper(): DriverPostMapper {
        return DriverPostMapper()
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceDataStoreFactory(placeRemoteDataStore: PlaceRemoteDataStore): PlaceDataStoreFactory {
        return PlaceDataStoreFactory(placeRemoteDataStore)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostDataStoreFactory(driverPostRemoteDataStore: DriverPostRemoteDataStore): DriverPostDataStoreFactory {
        return DriverPostDataStoreFactory(driverPostRemoteDataStore)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceRemoteDataStore(placeRemote: PlaceRemote): PlaceRemoteDataStore {
        return PlaceRemoteDataStore(placeRemote)
    }

    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostRemoteDataStore(driverDriverPostRemote: DriverPostRemote): DriverPostRemoteDataStore {
        return DriverPostRemoteDataStore(driverDriverPostRemote)
    }


    @Provides
    @MainScope
    @JvmStatic
    fun providePlaceRemote(apiService: ApiService,
                           placeMapper: com.badcompany.pitakpass.remote.mapper.PlaceMapper): PlaceRemote {
        return PlaceRemoteImpl(apiService, placeMapper)
    }


    @Provides
    @MainScope
    @JvmStatic
    fun provideDriverPostRemote(apiService: ApiService,
                                driverPostMapper: com.badcompany.pitakpass.remote.mapper.DriverPostMapper): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, driverPostMapper)
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemotePlaceMapper(): com.badcompany.pitakpass.remote.mapper.PlaceMapper {
        return com.badcompany.pitakpass.remote.mapper.PlaceMapper()
    }

    @MainScope
    @Provides
    @JvmStatic
    fun provideRemoteDriverPostMapper(): com.badcompany.pitakpass.remote.mapper.DriverPostMapper {
        return com.badcompany.pitakpass.remote.mapper.DriverPostMapper()
    }


}