package com.example.testforcodigo.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.example.testforcodigo.di.util.ViewModelKey
import com.example.testforcodigo.ui.main.WonderListViewModel
import com.example.testforcodigo.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WonderListViewModel::class)
    internal abstract fun bindListViewModel(wonderListViewModel: WonderListViewModel): ViewModel

/*
    @Binds
    @IntoMap
    @ViewModelKey(WonderDetailViewModel::class)
    internal abstract fun bindDetailViewModel(wonderDetailViewModel: WonderDetailViewModel): ViewModel
*/

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
