package com.emenike.randompassword.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val dataStore: DataStore<Preferences>,
): IRepository {
    override fun getPasswords(): Flow<Set<String>> = dataStore.data.map {
        it[Constants.PASSWORD_CREATED] ?: emptySet()
    }

    override suspend fun savePasswords(passwords: Set<String>) {
        dataStore.edit {
            it[Constants.PASSWORD_CREATED] = passwords
        }
    }
}