package com.emenike.randompassword.di

import com.emenike.randompassword.MainViewModel
import com.emenike.randompassword.data.repository.INetworkRepository
import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.data.repository.NetworkRepositoryImpl
import com.emenike.randompassword.data.repository.RemoteConfigRepo
import com.emenike.randompassword.data.repository.RemoteConfigRepoImpl
import com.emenike.randompassword.domain.interactors.GetDirectInformationUseCase
import com.emenike.randompassword.domain.interactors.GetPasswordsUseCase
import com.emenike.randompassword.domain.interactors.GetRemoteConfigUseCase
import com.emenike.randompassword.domain.interactors.SavePasswordsUseCase
import com.emenike.randompassword.domain.repository.RepositoryImpl
import com.emenike.randompassword.screens.randompassword.RandomPasswordViewModel
import com.emenike.randompassword.screens.savepassword.SavedPasswordViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect val platformModule: Module

val screenModelsModule = module {
    singleOf(::RandomPasswordViewModel)
    singleOf(::SavedPasswordViewModel)
    singleOf(::MainViewModel)
}

val repositoryModule = module {
    single<IRepository> { RepositoryImpl(get(), get(), get()) }
    single<RemoteConfigRepo> { RemoteConfigRepoImpl() }
    single<INetworkRepository> { NetworkRepositoryImpl(get()) }
}

val useCaseModule = module {
    factoryOf(::GetPasswordsUseCase)
    factoryOf(::SavePasswordsUseCase)
    singleOf(::GetRemoteConfigUseCase)
    singleOf(::GetDirectInformationUseCase)
}



val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val provideHttpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
        }
    }
}

fun initKoin() {
    startKoin {
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
