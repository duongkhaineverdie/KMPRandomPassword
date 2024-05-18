package com.emenike.randompassword.domain.interactors

import com.emenike.randompassword.data.model.DirectInformation
import com.emenike.randompassword.data.model.DirectParam
import com.emenike.randompassword.data.model.RemoteConfigs
import com.emenike.randompassword.data.repository.IRepository
import com.emenike.randompassword.domain.interactors.type.BaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetDirectInformationUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<DirectParam, DirectInformation>(dispatcher) {
    override suspend fun build(param: DirectParam): Flow<DirectInformation> = repository.getTitle(param)
}