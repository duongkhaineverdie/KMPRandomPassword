package com.emenike.randompassword.di

import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.data.repository.RemoteConfigRepo
import com.emenike.randompassword.data.repository.RemoteConfigRepoImpl
import com.emenike.randompassword.domain.interactors.GetPasswordsUseCase
import com.emenike.randompassword.domain.interactors.GetRemoteConfigUseCase
import com.emenike.randompassword.domain.interactors.SavePasswordsUseCase
import com.emenike.randompassword.domain.repository.RepositoryImpl
import com.emenike.randompassword.screens.randompassword.RandomPasswordViewModel
import com.emenike.randompassword.screens.savepassword.SavedPasswordViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val screenModelsModule = module {
    singleOf(::RandomPasswordViewModel)
    singleOf(::SavedPasswordViewModel)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImpl(get(), get()) }
    single<RemoteConfigRepo> { RemoteConfigRepoImpl() }
}

val useCaseModule = module {
    factoryOf(::GetPasswordsUseCase)
    factoryOf(::SavePasswordsUseCase)
    singleOf(::GetRemoteConfigUseCase)
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

expect val platformModule: Module


fun initKoin() {
    startKoin {
        modules(
            platformModule,
            repositoryModule,
            useCaseModule,
            screenModelsModule,
            dispatcherModule
        )
    }
}
