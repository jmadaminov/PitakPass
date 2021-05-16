package com.novatec.pitakpass.hilt

import com.novatec.pitakpass.BuildConfig
import com.novatec.pitakpass.remote.ApiService
import com.novatec.pitakpass.remote.ApiServiceFactory
import com.novatec.pitakpass.remote.AuthorizedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }


    @Singleton
    @Provides
    fun provideAuthorizedApiService(): AuthorizedApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG)
    }


}


