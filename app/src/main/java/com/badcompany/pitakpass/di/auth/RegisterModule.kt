package com.badcompany.pitakpass.di.auth

import com.badcompany.pitakpass.data.UserRepositoryImpl
import com.badcompany.pitakpass.data.repository.UserRemote
import com.badcompany.pitakpass.data.source.UserDataStoreFactory
import com.badcompany.pitakpass.data.source.UserRemoteDataStore
import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.domain.usecases.RegisterUser
import com.badcompany.pitakpass.BuildConfig
import com.badcompany.pitakpass.remote.ApiService
import com.badcompany.pitakpass.remote.ApiServiceFactory
import com.badcompany.pitakpass.remote.UserRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object RegisterModule {

    @AuthScope
    @Provides
    @JvmStatic
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
        return RegisterUser(userRepository)
    }

    @AuthScope
    @Provides
    @JvmStatic
    fun provideUserRepository(factory: UserDataStoreFactory): UserRepository {
        return UserRepositoryImpl(factory)
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
        return UserDataStoreFactory(userRemoteDataStore)
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
        return UserRemoteDataStore(userRemote)
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserRemote(apiService: ApiService): UserRemote {
        return UserRemoteImpl(apiService)
    }


}