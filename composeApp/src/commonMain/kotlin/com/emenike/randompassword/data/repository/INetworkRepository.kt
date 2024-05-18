package com.emenike.randompassword.data.repository

import com.emenike.randompassword.data.model.DirectInformation
import com.emenike.randompassword.data.model.DirectParam
import com.emenike.randompassword.data.model.RemoteConfigs
import kotlinx.coroutines.flow.Flow

interface INetworkRepository {

    suspend fun getTitle(directParam: DirectParam): Flow<DirectInformation>
}