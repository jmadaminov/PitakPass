package com.badcompany.pitakpass.di

import com.badcompany.pitakpass.data.CarRepositoryImpl
import com.badcompany.pitakpass.data.FileUploadRepositoryImpl
import com.badcompany.pitakpass.data.mapper.*
import com.badcompany.pitakpass.data.repository.CarRemote
import com.badcompany.pitakpass.data.repository.FileUploadRemote
import com.badcompany.pitakpass.data.source.CarDataStoreFactory
import com.badcompany.pitakpass.data.source.CarRemoteDataStore
import com.badcompany.pitakpass.data.source.FileUploadDataStoreFactory
import com.badcompany.pitakpass.data.source.FileUploadRemoteDataStore
import com.badcompany.pitakpass.domain.repository.CarRepository
import com.badcompany.pitakpass.domain.repository.FileUploadRepository
import com.badcompany.pitakpass.domain.usecases.*
import com.badcompany.pitakpass.BuildConfig
import com.badcompany.pitakpass.remote.ApiService
import com.badcompany.pitakpass.remote.ApiServiceFactory
import com.badcompany.pitakpass.remote.CarRemoteImpl
import com.badcompany.pitakpass.remote.FileUploadRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AppModule {


    @Provides
    @JvmStatic
    fun provideGetCars(carRepository: CarRepository): GetCars {
        return GetCars(carRepository)
    }

    @Provides
    @JvmStatic
    fun provideDeleteCar(carRepository: CarRepository): DeleteCar {
        return DeleteCar(carRepository)
    }


    @Provides
    @JvmStatic
    fun provideCarRepository(carDataStoreFactory: CarDataStoreFactory,
                             modelMapper: CarModelMapper,
                             colorMapper: CarColorMapper,
                             carMapper: CarMapper,
                             carDetailsMapper: CarDetailsMapper): CarRepository {
        return CarRepositoryImpl(carDataStoreFactory,
                                 colorMapper,
                                 modelMapper,
                                 carMapper,
                                 carDetailsMapper)
    }


    @Provides
    @JvmStatic
    fun provideCarColorMapper(): CarColorMapper {
        return CarColorMapper()
    }

    @Provides
    @JvmStatic
    fun provideCarDetailsMapper(): CarDetailsMapper {
        return CarDetailsMapper()
    }


    @Provides
    @JvmStatic
    fun provideCarMapper(): CarMapper {
        return CarMapper()
    }


    @Provides

    @JvmStatic
    fun provideCarModelMapper(): CarModelMapper {
        return CarModelMapper()
    }


    @Provides
    @JvmStatic
    fun provideCarDataStoreFactory(carRemoteDataStore: CarRemoteDataStore): CarDataStoreFactory {
        return CarDataStoreFactory(carRemoteDataStore)
    }


    @Provides

    @JvmStatic
    fun provideCarRemoteDataStore(carRemote: CarRemote): CarRemoteDataStore {
        return CarRemoteDataStore(carRemote)
    }


    @Provides
    @JvmStatic
    fun provideCarRemote(apiService: ApiService,
                         carModelMapper: com.badcompany.pitakpass.remote.mapper.CarModelMapper,
                         carColorMapper: com.badcompany.pitakpass.remote.mapper.CarColorMapper,
                         carMapper: com.badcompany.pitakpass.remote.mapper.CarMapper,
                         carDetailsMapper: com.badcompany.pitakpass.remote.mapper.CarDetailsMapper): CarRemote {
        return CarRemoteImpl(apiService,
                             carModelMapper,
                             carColorMapper,
                             carMapper,
                             carDetailsMapper)
    }


    @Provides

    @JvmStatic
    fun provideRemoteCarModelMapper(): com.badcompany.pitakpass.remote.mapper.CarModelMapper {
        return com.badcompany.pitakpass.remote.mapper.CarModelMapper()
    }


    @Provides
    @JvmStatic
    fun provideRemoteCarColorMapper(): com.badcompany.pitakpass.remote.mapper.CarColorMapper {
        return com.badcompany.pitakpass.remote.mapper.CarColorMapper()
    }


    @Provides
    @JvmStatic
    fun provideRemoteCarMapper(): com.badcompany.pitakpass.remote.mapper.CarMapper {
        return com.badcompany.pitakpass.remote.mapper.CarMapper()
    }


    @Provides
    @JvmStatic
    fun provideSaveCar(carRepository: CarRepository): SaveCar {
        return SaveCar(carRepository)
    }


    @Provides
    @JvmStatic
    fun provideSetDefaultCar(carRepository: CarRepository): SetDefaultCar {
        return SetDefaultCar(carRepository)
    }


    @Provides
    @JvmStatic
    fun provideGetCarColorsUseCase(carRepository: CarRepository): GetCarColors {
        return GetCarColors(carRepository)
    }


    @Provides
    @JvmStatic
    fun provideGetCarModelsUseCase(carRepository: CarRepository): GetCarModels {
        return GetCarModels(carRepository)
    }


    @Provides
    @JvmStatic
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }


    @Provides
    @JvmStatic
    fun provideUploadCarPhotoUseCase(fileUploadRepository: FileUploadRepository): UploadPhoto {
        return UploadPhoto(fileUploadRepository)
    }

    @Provides
    @JvmStatic
    fun provideFileUploadRepository(fileUploadDataStoreFactory: FileUploadDataStoreFactory,
                                    photoMapper: PhotoMapper): FileUploadRepository {
        return FileUploadRepositoryImpl(fileUploadDataStoreFactory, photoMapper)
    }


    @Provides
    @JvmStatic
    fun providePhotoMapper(): PhotoMapper {
        return PhotoMapper()
    }

    @Provides
    @JvmStatic
    fun provideFileUploadDataStoreFactory(fileUploadRemoteDataStore: FileUploadRemoteDataStore): FileUploadDataStoreFactory {
        return FileUploadDataStoreFactory(fileUploadRemoteDataStore)
    }

    @Provides
    @JvmStatic
    fun provideFileUploadRemoteDataStore(fileUploadRemote: FileUploadRemote): FileUploadRemoteDataStore {
        return FileUploadRemoteDataStore(fileUploadRemote)
    }

    @Provides
    @JvmStatic
    fun provideFileUploadRemote(apiService: ApiService,
                                photoMapper: com.badcompany.pitakpass.remote.mapper.PhotoMapper): FileUploadRemote {
        return FileUploadRemoteImpl(apiService, photoMapper)
    }

    @Provides
    @JvmStatic
    fun provideRemoteFileUploadPhotoMapper(): com.badcompany.pitakpass.remote.mapper.PhotoMapper {
        return com.badcompany.pitakpass.remote.mapper.PhotoMapper()
    }


//    @Singleton
//    @Provides
//    @JvmStatic
//    fun provideRequestOptions(): RequestOptions {
//        return RequestOptions
//            .placeholderOf(R.drawable.white_background)
//            .error(R.drawable.white_background)
//    }

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideGlideInstance(application: Application?,
//                             requestOptions: RequestOptions?): RequestManager {
//        return Glide.with(application!!)
//            .setDefaultRequestOptions(requestOptions!!)
//    }

//    @JvmStatic
//    @Singleton
//    @Provides
//    fun provideAppDrawable(application: Application?): Drawable {
//        return ContextCompat.getDrawable(application!!, R.drawable.logo)
//    }

}