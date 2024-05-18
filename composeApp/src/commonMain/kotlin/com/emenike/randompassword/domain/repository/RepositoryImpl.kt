package com.emenike.randompassword.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.emenike.randompassword.data.model.DirectInformation
import com.emenike.randompassword.data.model.DirectParam
import com.emenike.randompassword.data.repository.INetworkRepository
import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.data.repository.RemoteConfigRepo
import com.emenike.randompassword.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val remoteConfigRepo: RemoteConfigRepo,
    private val iNetworkRepository: INetworkRepository,
): IRepository {
    override fun getPasswords(): Flow<Set<String>> = dataStore.data.map {
        it[Constants.PASSWORD_CREATED] ?: emptySet()
    }

    override suspend fun savePasswords(passwords: Set<String>) {
        dataStore.edit {
            it[Constants.PASSWORD_CREATED] = passwords
        }
    }

    override fun getConfigs() = remoteConfigRepo.getConfigs()
    override suspend fun getTitle(directParam: DirectParam): Flow<DirectInformation> = iNetworkRepository.getTitle(directParam)
}