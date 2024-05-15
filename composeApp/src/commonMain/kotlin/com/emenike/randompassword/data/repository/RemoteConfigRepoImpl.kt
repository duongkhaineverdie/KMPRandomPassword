package com.emenike.randompassword.data.repository

import com.emenike.randompassword.data.model.RemoteConfigs
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RemoteConfigRepoImpl : RemoteConfigRepo {

    // Get remote config instance
    private val remoteConfig = Firebase.remoteConfig

    private val _configs = MutableStateFlow(RemoteConfigs())
    private val configs = _configs.asStateFlow()

    init {
        initConfigs()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun initConfigs() {
        GlobalScope.launch {
            Firebase.remoteConfig.settings {
                minimumFetchIntervalInSeconds = 0
            }
            Firebase.remoteConfig.fetchAndActivate()
            _configs.update {
                it.copy(
                    layoutVersion = remoteConfig.getValue("layout_version").asString(),
                )
            }
        }
    }

    /**
     * @return [RemoteConfigs] remote values
     * */
    override fun getConfigs() = flow {
        configs.collect {
            emit(it)
        }
    }
}