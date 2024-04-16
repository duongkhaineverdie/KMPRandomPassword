package com.emenike.randompassword.di

import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.domain.interactors.GetPasswordsUseCase
import com.emenike.randompassword.domain.interactors.SavePasswordsUseCase
import com.emenike.randompassword.domain.repository.RepositoryImpl
import com.emenike.randompassword.screens.randompassword.RandomPasswordViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val screenModelsModule = module {
    singleOf(::RandomPasswordViewModel)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { GetPasswordsUseCase(get(), get()) }
    factory { SavePasswordsUseCase(get(), get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

expect val platformModule: Module


fun initKoin() {
    startKoin {
        modules(
            com.emenike.randompassword.di.platformModule,
            repositoryModule,
            useCaseModule,
            screenModelsModule,
            dispatcherModule
        )
    }
}
