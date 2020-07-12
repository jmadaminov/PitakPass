package com.badcompany.pitakpass.di.auth

import com.badcompany.pitakpass.di.addPost.AddPostFragmentsModule
import com.badcompany.pitakpass.ui.auth.AuthActivity
import dagger.Subcomponent

@AuthScope
@Subcomponent(modules = [LoginModule::class, RegisterModule::class, AuthViewModelModule::class, AuthFragmentsModule::class])
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(authActivity: AuthActivity)

}
