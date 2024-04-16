package com.emenike.randompassword.data.repository

import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getPasswords(): Flow<Set<String>>
    suspend fun savePasswords(passwords: Set<String>)
}