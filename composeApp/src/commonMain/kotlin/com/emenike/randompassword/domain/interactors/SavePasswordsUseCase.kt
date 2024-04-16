package com.emenike.randompassword.domain.interactors

import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SavePasswordsUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Set<String>, Unit>(dispatcher) {
    override suspend fun block(param: Set<String>): Unit = repository.savePasswords(param)
}