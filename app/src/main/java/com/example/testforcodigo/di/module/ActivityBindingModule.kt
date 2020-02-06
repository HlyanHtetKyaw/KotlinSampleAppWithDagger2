package com.example.testforcodigo.di.module

import com.example.testforcodigo.ui.main.MainActivity
import com.example.testforcodigo.ui.main.MainFragmentBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    abstract fun bindMainActivity(): MainActivity

    /*@ContributesAndroidInjector
    internal abstract fun bindUserDetailActivity(): DetailViewActivity
*/
}