package com.example.testforcodigo.di.util

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@MapKey
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
