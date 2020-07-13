package com.badcompany.pitakpass.di.auth

import com.badcompany.pitakpass.data.UserRepositoryImpl
import com.badcompany.pitakpass.data.mapper.AuthMapper
import com.badcompany.pitakpass.data.mapper.UserCredentialsMapper
import com.badcompany.pitakpass.data.mapper.UserMapper
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
    fun provideUserRepository(factory: UserDataStoreFactory,
                              userMapper: UserMapper,
                              userCredentialsMapper: UserCredentialsMapper, authMapper:AuthMapper): UserRepository {
        return UserRepositoryImpl(factory, userMapper, userCredentialsMapper, authMapper)
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }
    @Provides
    @AuthScope
    @JvmStatic
    fun provideAuthMapper(): AuthMapper {
        return AuthMapper()
    }

    @Provides
    @AuthScope
    @JvmStatic
    fun provideUserCredentialsMapper(): UserCredentialsMapper {
        return UserCredentialsMapper()
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
    fun provideUserRemote(apiService: ApiService,
                          userCredMapper: com.badcompany.pitakpass.remote.mapper.UserCredentialsMapper,
                          userMapper: com.badcompany.pitakpass.remote.mapper.UserMapper,
                          authMapper: com.badcompany.pitakpass.remote.mapper.AuthMapper): UserRemote {
        return UserRemoteImpl(apiService, userCredMapper, userMapper, authMapper)
    }

    @AuthScope
    @Provides
    @JvmStatic
    fun provideRemoteUserCredentialsMapper(): com.badcompany.pitakpass.remote.mapper.UserCredentialsMapper {
        return com.badcompany.pitakpass.remote.mapper.UserCredentialsMapper()
    }

    @AuthScope
    @Provides
    @JvmStatic
    fun provideRemoteUserMapper(): com.badcompany.pitakpass.remote.mapper.UserMapper {
        return com.badcompany.pitakpass.remote.mapper.UserMapper()
    }


    @AuthScope
    @Provides
    @JvmStatic
    fun provideRemoteAuthMapper(): com.badcompany.pitakpass.remote.mapper.AuthMapper {
        return com.badcompany.pitakpass.remote.mapper.AuthMapper()
    }

//    @Provides
//    @AuthScope
//    @JvmStatic
//    fun provideApiService(): ApiService {
//        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
//    }

}