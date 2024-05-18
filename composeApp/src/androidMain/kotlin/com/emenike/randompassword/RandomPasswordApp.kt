package com.emenike.randompassword

import android.app.Application
import com.emenike.randompassword.di.dispatcherModule
import com.emenike.randompassword.di.platformModule
import com.emenike.randompassword.di.provideHttpClientModule
import com.emenike.randompassword.di.repositoryModule
import com.emenike.randompassword.di.screenModelsModule
import com.emenike.randompassword.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RandomPasswordApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RandomPasswordApp)
            modules(
                platformModule,
                repositoryModule,
                useCaseModule,
                screenModelsModule,
                dispatcherModule,
                provideHttpClientModule
            )
        }
    }
}
