package com.emenike.randompassword.data.repository

import com.emenike.randompassword.data.model.RemoteConfigs
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getPasswords(): Flow<Set<String>>
    suspend fun savePasswords(passwords: Set<String>)
    fun getConfigs(): Flow<RemoteConfigs>
}