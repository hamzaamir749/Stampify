package com.coderpakistan.design.presentation.di

import com.coderpakistan.design.presentation.camera.CameraViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val stampifyPresentationModule = module {
    viewModelOf(::CameraViewModel)
}