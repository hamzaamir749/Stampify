package com.coderpakistan.stampify

import android.app.Application
import com.coderpakistan.design.location.di.locationModule
import com.coderpakistan.design.presentation.di.stampifyPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin

class StampifyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StampifyApp)
            workManagerFactory()
            modules(
                locationModule,
                stampifyPresentationModule
            )
        }
    }
}