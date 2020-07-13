package com.badcompany.pitakpass.di.addPost

import com.badcompany.pitakpass.data.DriverPostRepositoryImpl
import com.badcompany.pitakpass.data.PlaceRepositoryImpl
import com.badcompany.pitakpass.data.mapper.DriverPostMapper
import com.badcompany.pitakpass.data.mapper.PlaceMapper
import com.badcompany.pitakpass.data.repository.PlaceRemote
import com.badcompany.pitakpass.data.repository.DriverPostRemote
import com.badcompany.pitakpass.data.source.DriverPostDataStoreFactory
import com.badcompany.pitakpass.data.source.DriverPostRemoteDataStore
import com.badcompany.pitakpass.data.source.PlaceDataStoreFactory
import com.badcompany.pitakpass.data.source.PlaceRemoteDataStore
import com.badcompany.pitakpass.domain.repository.DriverPostRepository
import com.badcompany.pitakpass.domain.repository.PlaceRepository
import com.badcompany.pitakpass.domain.usecases.CreateDriverPost
import com.badcompany.pitakpass.domain.usecases.GetPlacesFeed
import com.badcompany.pitakpass.remote.ApiService
import com.badcompany.pitakpass.remote.DriverPostRemoteImpl
import com.badcompany.pitakpass.remote.PlaceRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AddPostModule {

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideCreateDriverPost(driverPostRepository: DriverPostRepository): CreateDriverPost {
        return CreateDriverPost(driverPostRepository)
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
    fun providePlaceRepository(factory: PlaceDataStoreFactory,
                               placeMapper: PlaceMapper): PlaceRepository {
        return PlaceRepositoryImpl(factory, placeMapper)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideDriverPostRepository(factory: DriverPostDataStoreFactory,
                                    driverPostMapper: DriverPostMapper): DriverPostRepository {
        return DriverPostRepositoryImpl(factory, driverPostMapper)
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceMapper(): PlaceMapper {
        return PlaceMapper()
    }

    @Provides
    @AddPostScope
    @JvmStatic
    fun provideDriverPostMapper(): DriverPostMapper {
        return DriverPostMapper()
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
    fun provideDriverPostDataStoreFactory(driverPostRemoteDataStore: DriverPostRemoteDataStore): DriverPostDataStoreFactory {
        return DriverPostDataStoreFactory(driverPostRemoteDataStore)
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
    fun provideDriverPostRemoteDataStore(driverDriverPostRemote: DriverPostRemote): DriverPostRemoteDataStore {
        return DriverPostRemoteDataStore(driverDriverPostRemote)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun providePlaceRemote(apiService: ApiService,
                           placeMapper: com.badcompany.pitakpass.remote.mapper.PlaceMapper): PlaceRemote {
        return PlaceRemoteImpl(apiService, placeMapper)
    }


    @Provides
    @AddPostScope
    @JvmStatic
    fun provideDriverPostRemote(apiService: ApiService,
                                driverPostMapper: com.badcompany.pitakpass.remote.mapper.DriverPostMapper): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, driverPostMapper)
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideRemotePlaceMapper(): com.badcompany.pitakpass.remote.mapper.PlaceMapper {
        return com.badcompany.pitakpass.remote.mapper.PlaceMapper()
    }

    @AddPostScope
    @Provides
    @JvmStatic
    fun provideRemoteDriverPostMapper(): com.badcompany.pitakpass.remote.mapper.DriverPostMapper {
        return com.badcompany.pitakpass.remote.mapper.DriverPostMapper()
    }


}