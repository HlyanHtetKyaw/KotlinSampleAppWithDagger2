package com.example.testforcodigo.ui.main

import com.example.testforcodigo.ui.list.WonderListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideListFragment(): WonderListFragment
}