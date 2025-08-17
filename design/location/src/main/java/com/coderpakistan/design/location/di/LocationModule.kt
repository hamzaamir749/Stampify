package com.coderpakistan.design.location.di

import com.coderpakistan.design.domain.LocationObserver
import com.coderpakistan.design.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}