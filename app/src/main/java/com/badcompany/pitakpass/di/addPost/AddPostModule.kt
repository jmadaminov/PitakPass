package com.badcompany.pitakpass.di.addPost

import com.badcompany.pitakpass.data.PassengerPostRepositoryImpl
import com.badcompany.pitakpass.data.PlaceRepositoryImpl
import com.badcompany.pitakpass.data.repository.PlaceRemote
import com.badcompany.pitakpass.data.repository.PassengerPostRemote
import com.badcompany.pitakpass.data.source.*
import com.badcompany.pitakpass.domain.repository.PassengerPostRepository
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import com.badcompany.pitakpass.domain.usecases.CreatePassengerPost
import com.badcompany.pitakpass.domain.usecases.GetPlacesFeed
import com.badcompany.pitakpass.remote.ApiService
import com.badcompany.pitakpass.remote.PassengerPostRemoteImpl
import com.badcompany.pitakpass.remote.PlaceRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AddPostModule {

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideCreatePassengerPost(driverPostRepository: PassengerPostRepository): CreatePassengerPost {
        return CreatePassengerPost(driverPostRepository)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideGetPlacesFeed(placeRepository: PlaceRepository): GetPlacesFeed {
        return GetPlacesFeed(placeRepository)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun providePlaceRepository(factory: PlaceDataStoreFactory): PlaceRepository {
        return PlaceRepositoryImpl(factory)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun providePassengerPostRepository(factory: PassengerPostDataStoreFactory): PassengerPostRepository {
        return PassengerPostRepositoryImpl(factory)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceDataStoreFactory(placeRemoteDataStore: PlaceRemoteDataStore): PlaceDataStoreFactory {
        return PlaceDataStoreFactory(placeRemoteDataStore)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun providePassengerPostDataStoreFactory(driverPostRemoteDataStore: PassengerPostRemoteDataStore): PassengerPostDataStoreFactory {
        return PassengerPostDataStoreFactory(driverPostRemoteDataStore)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceRemoteDataStore(placeRemote: PlaceRemote): PlaceRemoteDataStore {
        return PlaceRemoteDataStore(placeRemote)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun providePassengerPostRemoteDataStore(passengerPassengerPostRemote: PassengerPostRemote): PassengerPostRemoteDataStore {
        return PassengerPostRemoteDataStore(passengerPassengerPostRemote)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceRemote(apiService: ApiService): PlaceRemote {
        return PlaceRemoteImpl(apiService)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun providePassengerPostRemote(apiService: ApiService): PassengerPostRemote {
        return PassengerPostRemoteImpl(apiService)
    }

}