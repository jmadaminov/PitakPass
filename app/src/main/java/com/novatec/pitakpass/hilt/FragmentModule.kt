package com.novatec.pitakpass.hilt
//import com.example.benefit.ui.auth.LoginFragmentFactory
//import com.novatec.data.repository.UserRemote
//import com.novatec.remote.UserRemoteImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class FragmentModule {

//    @JvmStatic
//    @Singleton
//    @Provides
//    @Named("LoginFragmentFactory")
//    fun provideLoginFragmentFactory(): FragmentFactory {
//        return LoginFragmentFactory()
//    }

//    @Binds
//    abstract fun provideUserRepository(userRemoteImpl: UserRemoteImpl): UserRemote

//    @Binds
//    abstract fun providePartnersRepository(partnersRemoteImpl: PartnersRemoteImpl): PartnersRemote


}




