package com.emenike.randompassword.di

import com.emenike.randompassword.data.factory.dataStorePreferences
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::dataStorePreferences)
}