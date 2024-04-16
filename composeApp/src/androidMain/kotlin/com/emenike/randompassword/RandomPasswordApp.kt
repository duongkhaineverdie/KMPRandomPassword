package com.emenike.randompassword

import android.app.Application
import com.emenike.randompassword.di.initKoin

class RandomPasswordApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
