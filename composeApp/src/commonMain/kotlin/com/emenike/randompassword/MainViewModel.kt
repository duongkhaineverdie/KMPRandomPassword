package com.emenike.randompassword

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.emenike.randompassword.data.model.DirectParam
import com.emenike.randompassword.domain.interactors.GetDirectInformationUseCase
import com.emenike.randompassword.domain.interactors.GetRemoteConfigUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    val getRemoteConfigUseCase: GetRemoteConfigUseCase,
    val getDirectInformationUseCase: GetDirectInformationUseCase,
) : ScreenModel {
    private val _uiState = MutableStateFlow(RandomPasswordUiState())
    val uiState: StateFlow<RandomPasswordUiState> = _uiState.asStateFlow()

    init {
        screenModelScope.launch {
            getRemoteConfigUseCase(Unit).collectLatest { resultRemoteConfig ->
                resultRemoteConfig.onSuccess { remoteConfig ->
                    val secretKey = remoteConfig.secretKey
                    val directCheck = remoteConfig.directCheck
                    if (secretKey?.isNotBlank() == true && directCheck?.isNotBlank() == true) {
                        val directParam =
                            DirectParam(secretKey = secretKey, directCheck = directCheck)
                        getDirectInformationUseCase(directParam).collectLatest { result ->
                            result.onSuccess { directInformation ->
                                _uiState.update { state ->
                                    state.copy(
                                        listSetting = directInformation.title
                                    )
                                }
                            }.onFailure {
                                println("Error: $it")
                            }
                        }
                        _uiState.update { state ->
                            state.copy(
                                layoutVersion = remoteConfig.layoutVersion,
                                direct = remoteConfig.direct,
                                regexCheck = remoteConfig.regexCheck,
                            )
                        }
                    }
                }
            }
        }


    }

    data class RandomPasswordUiState(
        val layoutVersion: String? = null,
        val direct: String? = null,
        val regexCheck: String? = null,
        val listSetting: String? = null,
    )
}