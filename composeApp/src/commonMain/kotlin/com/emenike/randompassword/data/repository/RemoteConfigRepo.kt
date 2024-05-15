package com.emenike.randompassword.data.repository

import com.emenike.randompassword.data.model.RemoteConfigs
import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepo {

    fun initConfigs()

    fun getConfigs(): Flow<RemoteConfigs>
}