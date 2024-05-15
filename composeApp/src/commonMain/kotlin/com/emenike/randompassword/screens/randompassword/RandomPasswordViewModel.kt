package com.emenike.randompassword.screens.randompassword

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.emenike.randompassword.domain.interactors.GetPasswordsUseCase
import com.emenike.randompassword.domain.interactors.GetRemoteConfigUseCase
import com.emenike.randompassword.domain.interactors.SavePasswordsUseCase
import com.emenike.randompassword.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RandomPasswordViewModel(
    val getPasswordsUseCase: GetPasswordsUseCase,
    val savePasswordsUseCase: SavePasswordsUseCase,
    val getRemoteConfigUseCase: GetRemoteConfigUseCase,
) : ScreenModel {
    private val _uiState = MutableStateFlow(RandomPasswordUiState())
    val uiState: StateFlow<RandomPasswordUiState> = _uiState.asStateFlow()

    fun onValueChangeLength(value: String) {
        _uiState.update {
            it.copy(
                passwordLength = value.toIntOrNull()
            )
        }
    }

    init {
        screenModelScope.launch {
            getRemoteConfigUseCase(Unit).collectLatest {
                it.onSuccess { remoteConfig ->
                    _uiState.update { state ->
                        state.copy(
                            layoutVersion = remoteConfig.layoutVersion
                        )
                    }
                }
            }
            getPasswordsUseCase(Unit).collectLatest {
                it.onSuccess { passwords ->
                    _uiState.update { state ->
                        state.copy(
                            passwords = passwords.toList()
                        )
                    }
                }
            }
        }
    }

    fun actionIncludeLowercase(boolean: Boolean) {
        if (boolean) {
            _uiState.update {
                it.copy(includeLowercase = true)
            }
        } else {
            if (onOneIncludeSelected())
                _uiState.update {
                    it.copy(
                        showDialogNotificationAtLeast = true
                    )
                }
            else {
                _uiState.update {
                    it.copy(includeLowercase = false)
                }
            }
        }
    }

    fun actionIncludeUppercase(boolean: Boolean) {
        if (boolean) {
            _uiState.update {
                it.copy(includeUppercase = true)
            }
        } else {
            if (onOneIncludeSelected())
                _uiState.update {
                    it.copy(
                        showDialogNotificationAtLeast = true
                    )
                }
            else {
                _uiState.update {
                    it.copy(includeUppercase = false)
                }
            }
        }
    }

    fun actionIncludeDigits(boolean: Boolean) {
        if (boolean) {
            _uiState.update {
                it.copy(includeDigits = true)
            }
        } else {
            if (onOneIncludeSelected())
                _uiState.update {
                    it.copy(
                        showDialogNotificationAtLeast = true
                    )
                }
            else {
                _uiState.update {
                    it.copy(includeDigits = false)
                }
            }
        }
    }

    fun actionIncludeSpecialChars(boolean: Boolean) {
        if (boolean) {
            _uiState.update {
                it.copy(includeSpecialChars = true)
            }
        } else {
            if (onOneIncludeSelected())
                _uiState.update {
                    it.copy(
                        showDialogNotificationAtLeast = true
                    )
                }
            else {
                _uiState.update {
                    it.copy(includeSpecialChars = false)
                }
            }
        }
    }

    fun generatePassword() {
        uiState.value.passwordLength?.let {
            if (it in 6..16) {
                val characterPool = buildString {
                    if (uiState.value.includeLowercase) append(Constants.LOWERCASE_LETTERS)
                    if (uiState.value.includeUppercase) append(Constants.UPPERCASE_LETTERS)
                    if (uiState.value.includeDigits) append(Constants.DIGITS)
                    if (uiState.value.includeSpecialChars) append(Constants.SPECIAL_CHAR)
                }

                // Generate random password using string concatenation
                val generatedPassword = (1..it)
                    .map { _ ->
                        characterPool.random()
                    }
                    .joinToString("")

                _uiState.update { state ->
                    state.copy(
                        passwordGenerated = generatedPassword,
                        isErrorInput = false,
                        isSavedPassword = false
                    )
                }
            } else {
                _uiState.update { state ->
                    state.copy(isErrorInput = true)
                }
            }
        } ?: run {
            _uiState.update { state ->
                state.copy(isErrorInput = true)
            }
        }
    }

    private fun onOneIncludeSelected(): Boolean {
        val countTrue = listOf(
            uiState.value.includeLowercase,
            uiState.value.includeUppercase,
            uiState.value.includeDigits,
            uiState.value.includeSpecialChars
        ).count { it }
        return countTrue == 1
    }

    fun savePassword(password: String) {
        val passwords = uiState.value.passwords
        if (!passwords.contains(password)) {
            val newPasswords = passwords + password
            screenModelScope.launch {
                savePasswordsUseCase(newPasswords.toSet()).onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            isSavedPassword = true,
                            isShowSavedDialog = true,
                        )
                    }
                }
            }
        }
    }

    fun onDismissDialogAtLeast() {
        _uiState.update {
            it.copy(
                showDialogNotificationAtLeast = false
            )
        }
    }

    fun onDismissSavedDialog() {
        _uiState.update {
            it.copy(
                isShowSavedDialog = false
            )
        }
    }
}

data class RandomPasswordUiState(
    val passwordLength: Int? = null,
    val includeLowercase: Boolean = true,
    val includeUppercase: Boolean = true,
    val includeDigits: Boolean = true,
    val includeSpecialChars: Boolean = true,
    val passwordGenerated: String = "",
    val isErrorInput: Boolean = false,
    val showDialogNotificationAtLeast: Boolean = false,
    val isSavedPassword: Boolean = false,
    val isShowSavedDialog: Boolean = false,
    val passwords: List<String> = arrayListOf(),
    val layoutVersion: String? = null,
)
