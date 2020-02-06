package com.example.testforcodigo.di.component

import android.app.Application
import com.example.testforcodigo.base.BaseApplication
import com.example.testforcodigo.di.module.ActivityBindingModule
import com.example.testforcodigo.di.module.ApplicationModule
import com.example.testforcodigo.di.module.ContextModule
import com.example.testforcodigo.di.module.WonderDbModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class, WonderDbModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}