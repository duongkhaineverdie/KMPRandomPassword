package com.emenike.randompassword.screens.savepassword

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.emenike.randompassword.domain.interactors.GetPasswordsUseCase
import com.emenike.randompassword.domain.interactors.SavePasswordsUseCase
import com.emenike.randompassword.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedPasswordViewModel(
    val getPasswordsUseCase: GetPasswordsUseCase,
    val savePasswordsUseCase: SavePasswordsUseCase,
) : ScreenModel {
    private val _uiState = MutableStateFlow(RandomPasswordUiState())
    val uiState: StateFlow<RandomPasswordUiState> = _uiState.asStateFlow()

    init {
        getPasswords()
    }

    private fun getPasswords() {
        screenModelScope.launch {
            getPasswordsUseCase(Unit).collectLatest {
                it.onSuccess { passwords ->
                    _uiState.update { state ->
                        state.copy(
                            passwords = passwords.toMutableList(),
                        )
                    }
                }
            }
        }
    }

    fun selectedPasswordDelete(pass: String) {
        val passwords = uiState.value.passwordsDelete.toList()
        val result = if (passwords.contains(pass)) {
            passwords - pass
        } else {
            passwords + pass
        }
        _uiState.update {
            it.copy(
                passwordsDelete = result.toMutableList()
            )
        }
    }

    fun actionDeleteMode() {
        _uiState.update {
            it.copy(
                isDeleteMode = !uiState.value.isDeleteMode,
                passwordsDelete = arrayListOf()
            )
        }
    }

    fun deletePasswords(passwordsDelete: List<String>) {
        val listPasswordAfter = uiState.value.passwords - passwordsDelete.toSet()
        screenModelScope.launch {
            savePasswordsUseCase(listPasswordAfter.toSet()).onSuccess {
                getPasswords()
                _uiState.update {
                    it.copy(
                        passwordsDelete = arrayListOf(),
                        isDeleteMode = false
                    )
                }
            }
        }
    }
}

data class RandomPasswordUiState(
    val passwords: MutableList<String> = arrayListOf(),
    val passwordsDelete: MutableList<String> = arrayListOf(),
    val isDeleteMode: Boolean = false,
)
