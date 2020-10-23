package com.badcompany.pitakpass.hilt

import com.badcompany.pitakpass.BuildConfig
import com.badcompany.pitakpass.remote.ApiService
import com.badcompany.pitakpass.remote.ApiServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }


//    @Singleton
//    @Provides
//    fun provideAuthorizedApiService(): AuthorizedApiService {
//        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG)
//    }


}


