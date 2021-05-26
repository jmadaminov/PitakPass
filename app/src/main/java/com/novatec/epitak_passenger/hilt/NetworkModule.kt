package com.novatec.epitak_passenger.hilt

import com.novatec.epitak_passenger.BuildConfig
import com.novatec.epitak_passenger.remote.ApiService
import com.novatec.epitak_passenger.remote.ApiServiceFactory
import com.novatec.epitak_passenger.remote.AuthApiService
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
    fun provideAuthorizedApiService(): AuthApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG)
    }


}


