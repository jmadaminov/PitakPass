package com.badcompany.pitakpass.di

import android.app.Application
import com.badcompany.pitakpass.di.addPost.AddPostComponent
import com.badcompany.pitakpass.di.addcar.AddCarComponent
import com.badcompany.pitakpass.di.auth.AuthComponent
import com.badcompany.pitakpass.di.main.MainComponent
import com.badcompany.pitakpass.ui.BaseActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, SubComponentsModule::class])
interface AppComponent {

    @Component.Builder interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }


    fun inject(activity: BaseActivity)
    fun authComponent(): AuthComponent.Factory
    fun mainComponent(): MainComponent.Factory
    fun addCarComponent(): AddCarComponent.Factory
    fun addPostComponent(): AddPostComponent.Factory

}
