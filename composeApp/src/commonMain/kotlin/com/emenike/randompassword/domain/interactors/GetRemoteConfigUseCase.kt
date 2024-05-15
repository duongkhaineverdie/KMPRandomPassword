package com.emenike.randompassword.domain.interactors

import com.emenike.randompassword.data.model.RemoteConfigs
import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.domain.interactors.type.BaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetRemoteConfigUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, RemoteConfigs>(dispatcher) {
    override suspend fun build(param: Unit): Flow<RemoteConfigs> = repository.getConfigs()
}