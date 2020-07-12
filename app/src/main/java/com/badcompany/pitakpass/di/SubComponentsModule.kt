package com.badcompany.pitakpass.di

import com.badcompany.pitakpass.di.addPost.AddPostComponent
import com.badcompany.pitakpass.di.addcar.AddCarComponent
import com.badcompany.pitakpass.di.auth.AuthComponent
import com.badcompany.pitakpass.di.main.MainComponent
import dagger.Module

/**
 * Created by jahon on 09-Apr-20
 */

@Module(subcomponents = [AuthComponent::class, MainComponent::class, AddCarComponent::class, AddPostComponent::class])
class SubComponentsModule