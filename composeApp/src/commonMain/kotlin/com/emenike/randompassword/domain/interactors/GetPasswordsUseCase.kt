package com.emenike.randompassword.domain.interactors

import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.domain.interactors.type.BaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetPasswordsUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, Set<String>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<Set<String>> = repository.getPasswords()

}