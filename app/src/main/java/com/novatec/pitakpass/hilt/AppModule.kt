package com.novatec.pitakpass.hilt

import com.novatec.pitakpass.data.*
import com.novatec.pitakpass.data.repository.*
import com.novatec.pitakpass.data.source.*
import com.novatec.pitakpass.domain.repository.*
import com.novatec.pitakpass.domain.usecases.*
import com.novatec.pitakpass.remote.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideCarRepository(carDataStoreFactory: CarDataStoreFactory): CarRepository {
        return CarRepositoryImpl(carDataStoreFactory)
    }

    @Provides
    @Singleton
    fun provideCarDataStoreFactory(carRemoteDataStore: CarRemoteDataStore): CarDataStoreFactory {
        return CarDataStoreFactory(carRemoteDataStore)
    }


    @Provides

    @Singleton
    fun provideCarRemoteDataStore(carRemote: CarRemote): CarRemoteDataStore {
        return CarRemoteDataStore(carRemote)
    }


    @Provides
    @Singleton
    fun provideCarRemote(apiService: ApiService,
                         authorizedApiService: AuthorizedApiService): CarRemote {
        return CarRemoteImpl(apiService, authorizedApiService)
    }

    @Provides
    @Singleton
    fun provideGetCarColorsUseCase(carRepository: CarRepository): GetCarColors {
        return GetCarColors(carRepository)
    }

    @Provides
    @Singleton
    fun provideGetCarModelsUseCase(carRepository: CarRepository): GetCarModels {
        return GetCarModels(carRepository)
    }

    @Provides
    @Singleton
    fun provideUploadCarPhotoUseCase(fileUploadRepository: FileUploadRepository): UploadPhoto {
        return UploadPhoto(fileUploadRepository)
    }

    @Provides
    @Singleton
    fun provideFileUploadRepository(fileUploadDataStoreFactory: FileUploadDataStoreFactory): FileUploadRepository {
        return FileUploadRepositoryImpl(fileUploadDataStoreFactory)
    }


    @Provides
    @Singleton
    fun provideFileUploadDataStoreFactory(fileUploadRemoteDataStore: FileUploadRemoteDataStore): FileUploadDataStoreFactory {
        return FileUploadDataStoreFactory(fileUploadRemoteDataStore)
    }

    @Provides
    @Singleton
    fun provideFileUploadRemoteDataStore(fileUploadRemote: FileUploadRemote): FileUploadRemoteDataStore {
        return FileUploadRemoteDataStore(fileUploadRemote)
    }

    @Provides
    @Singleton
    fun provideFileUploadRemote(apiService: ApiService): FileUploadRemote {
        return FileUploadRemoteImpl(apiService)
    }


    @Provides
    @Singleton
    fun provideGetPlacesFeed(placeRepository: PlaceRepository): GetPlacesFeed {
        return GetPlacesFeed(placeRepository)
    }


    @Provides
    @Singleton
    fun providePlaceRepository(factory: PlaceDataStoreFactory): PlaceRepository {
        return PlaceRepositoryImpl(factory)
    }


//    @Provides
//    @Singleton
//    fun provideGetDriverPostWithFilter(passengerPostRepository: DriverPostRepository): GetDriverPostWithFilter {
//        return GetDriverPostWithFilter(passengerPostRepository)
//    }


    @Provides
    @Singleton
    fun providePassengerPostRepository(factory: PassengerPostDataStoreFactory): PassengerPostRepository {
        return PassengerPostRepositoryImpl(factory)
    }

    @Provides

    @Singleton
    fun providePassengerPostDataStoreFactory(postDataStore: PassengerPostDataStore): PassengerPostDataStoreFactory {
        return PassengerPostDataStoreFactory(postDataStore)
    }

    @Provides

    @Singleton
    fun providePassengerPostDataStore(passengerPostRemote: PassengerPostRemote): PassengerPostDataStore {
        return PassengerPostRemoteDataStore(passengerPostRemote)
    }

    @Provides

    @Singleton
    fun providePassengerPostRemote(apiService: ApiService,
                                   authorizedApiService: AuthorizedApiService): PassengerPostRemote {
        return PassengerPostRemoteImpl(apiService, authorizedApiService)
    }


    @Provides
    @Singleton
    fun provideGetActivePassengerPost(driverPostRepository: PassengerPostRepository): GetActivePassengerPost {
        return GetActivePassengerPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideDeletePassengerPost(driverPostRepository: PassengerPostRepository): DeletePassengerPost {
        return DeletePassengerPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideFinishPassengerPost(driverPostRepository: PassengerPostRepository): FinishPassengerPost {
        return FinishPassengerPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideGetHistoryPassengerPost(driverPostRepository: PassengerPostRepository): GetHistoryPassengerPost {
        return GetHistoryPassengerPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun provideDriverPostRepository(factory: DriverPostDataStoreFactory): DriverPostRepository {
        return DriverPostRepositoryImpl(factory)
    }

    @Provides

    @Singleton
    fun providePlaceDataStoreFactory(placeRemoteDataStore: PlaceRemoteDataStore): PlaceDataStoreFactory {
        return PlaceDataStoreFactory(placeRemoteDataStore)
    }

    @Provides

    @Singleton
    fun provideDriverPostDataStoreFactory(driverPostRemoteDataStore: DriverPostRemoteDataStore): DriverPostDataStoreFactory {
        return DriverPostDataStoreFactory(driverPostRemoteDataStore)
    }

    @Provides

    @Singleton
    fun providePlaceRemoteDataStore(placeRemote: PlaceRemote): PlaceRemoteDataStore {
        return PlaceRemoteDataStore(placeRemote)
    }

    @Provides

    @Singleton
    fun provideDriverPostRemoteDataStore(driverDriverPostRemote: DriverPostRemote): DriverPostRemoteDataStore {
        return DriverPostRemoteDataStore(driverDriverPostRemote)
    }


    @Provides

    @Singleton
    fun providePlaceRemote(apiService: ApiService,
                           authorizedApiService: AuthorizedApiService): PlaceRemote {
        return PlaceRemoteImpl(apiService, authorizedApiService)
    }


    @Provides

    @Singleton
    fun provideDriverPostRemote(apiService: ApiService,
                                authorizedApiService: AuthorizedApiService): DriverPostRemote {
        return DriverPostRemoteImpl(apiService, authorizedApiService)
    }


    @Provides
    @Singleton
    fun provideLogUserInUseCase(userRepository: UserRepository): LogUserIn {
        return LogUserIn(userRepository)
    }


    @Provides
    @Singleton
    fun provideSmsConfirm(userRepository: UserRepository): SmsConfirm {
        return SmsConfirm(userRepository)
    }


    @Provides
    @Singleton
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUser {
        return RegisterUser(userRepository)
    }


    @Provides
    @Singleton
    fun provideUserRepository(factory: UserDataStoreFactory): UserRepository {
        return UserRepositoryImpl(factory)
    }

    @Provides

    @Singleton
    fun provideUserDataStoreFactory(userRemoteDataStore: UserRemoteDataStore): UserDataStoreFactory {
        return UserDataStoreFactory(userRemoteDataStore)
    }

    @Provides

    @Singleton
    fun provideUserRemoteDataStore(userRemote: UserRemote): UserRemoteDataStore {
        return UserRemoteDataStore(userRemote)
    }

    @Provides

    @Singleton
    fun provideUserRemote(apiService: ApiService,
                          authorizedApiService: AuthorizedApiService): UserRemote {
        return UserRemoteImpl(apiService, authorizedApiService)
    }


    @Provides
    @Singleton
    fun provideCreatePassengerPost(driverPostRepository: PassengerPostRepository): CreatePassengerPost {
        return CreatePassengerPost(driverPostRepository)
    }


    @Provides
    @Singleton
    fun providePassengerPostRemoteDataStore(passengerPassengerPostRemote: PassengerPostRemote): PassengerPostRemoteDataStore {
        return PassengerPostRemoteDataStore(passengerPassengerPostRemote)
    }


}




