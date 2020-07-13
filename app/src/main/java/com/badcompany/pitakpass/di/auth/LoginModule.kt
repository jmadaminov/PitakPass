package com.badcompany.pitakpass.di.auth

import com.badcompany.pitakpass.domain.repository.UserRepository
import com.badcompany.pitakpass.domain.usecases.LogUserIn
import com.badcompany.pitakpass.domain.usecases.SmsConfirm
import dagger.Module
import dagger.Provides

@Module
object LoginModule {

    @AuthScope
    @Provides
    @JvmStatic
    fun provideLogUserInUseCase(userRepository: UserRepository): LogUserIn {
        return LogUserIn(userRepository)
    }


    @AuthScope
    @Provides
    @JvmStatic
    fun provideSmsConfirm(userRepository: UserRepository): SmsConfirm {
        return SmsConfirm(userRepository)
    }

}