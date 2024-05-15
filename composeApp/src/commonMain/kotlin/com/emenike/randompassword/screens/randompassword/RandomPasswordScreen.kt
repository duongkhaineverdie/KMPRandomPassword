package com.emenike.randompassword.screens.randompassword

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.emenike.randompassword.screens.component.DialogOk
import com.emenike.randompassword.screens.tab.HomeTab
import io.github.alexzhirkevich.cupertino.AlertActionStyle
import io.github.alexzhirkevich.cupertino.CupertinoBorderedTextField
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveAlertDialogNative
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveSwitch
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kmp_random_password.composeapp.generated.resources.Res
import kmp_random_password.composeapp.generated.resources.copied
import kmp_random_password.composeapp.generated.resources.generate_password_title_button
import kmp_random_password.composeapp.generated.resources.home_screen_message_dialog_delete
import kmp_random_password.composeapp.generated.resources.home_screen_ok_dialog
import kmp_random_password.composeapp.generated.resources.img_copy
import kmp_random_password.composeapp.generated.resources.img_download
import kmp_random_password.composeapp.generated.resources.input_error_message
import kmp_random_password.composeapp.generated.resources.lower_text_label
import kmp_random_password.composeapp.generated.resources.number_label
import kmp_random_password.composeapp.generated.resources.password_length
import kmp_random_password.composeapp.generated.resources.saved
import kmp_random_password.composeapp.generated.resources.saved_title_dialog_delete
import kmp_random_password.composeapp.generated.resources.special_char
import kmp_random_password.composeapp.generated.resources.upper_text_label
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data object RandomPasswordScreen : HomeTab {
    @OptIn(ExperimentalResourceApi::class, ExperimentalAdaptiveApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val homeViewModel: RandomPasswordViewModel = getScreenModel()
        val uiState by homeViewModel.uiState.collectAsState()
        val clipboardManager = LocalClipboardManager.current

        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }
        AdaptiveScaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onValueLengthChange = homeViewModel::onValueChangeLength,
                onClickGeneratePassword = homeViewModel::generatePassword,
                actionIncludeDigits = homeViewModel::actionIncludeDigits,
                actionIncludeUppercase = homeViewModel::actionIncludeUppercase,
                actionIncludeLowercase = homeViewModel::actionIncludeLowercase,
                actionIncludeSpecialChars = homeViewModel::actionIncludeSpecialChars,
                includeLowercase = uiState.includeLowercase,
                includeUppercase = uiState.includeUppercase,
                includeDigits = uiState.includeDigits,
                includeSpecialChars = uiState.includeSpecialChars,
                lengthPassword = uiState.passwordLength?.toString() ?: "",
                isErrorField = uiState.isErrorInput,
                generatedPassword = uiState.passwordGenerated,
                onClickCopy = {
                    clipboardManager.setText(AnnotatedString(it))
                    scope.launch {
                        snackBarHostState.showSnackbar(getString(Res.string.copied))
                    }
                },
                onClickSavePassword = homeViewModel::savePassword,
                onDismissDialogAtLeast = homeViewModel::onDismissDialogAtLeast,
                onDismissSavedDialog = homeViewModel::onDismissSavedDialog,
                showDialogAtLeast = uiState.showDialogNotificationAtLeast,
                isSaved = uiState.isSavedPassword,
                isShowSaved = uiState.isShowSavedDialog,
                layoutVersion = uiState.layoutVersion,
            )
        }
    }
}

@OptIn(
    ExperimentalResourceApi::class,
    ExperimentalAdaptiveApi::class
)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    lengthPassword: String = "",
    onClickGeneratePassword: () -> Unit,
    onValueLengthChange: (String) -> Unit,
    actionIncludeLowercase: (Boolean) -> Unit,
    actionIncludeUppercase: (Boolean) -> Unit,
    actionIncludeDigits: (Boolean) -> Unit,
    actionIncludeSpecialChars: (Boolean) -> Unit,
    includeLowercase: Boolean = true,
    includeUppercase: Boolean = true,
    includeDigits: Boolean = true,
    includeSpecialChars: Boolean = true,
    isErrorField: Boolean = true,
    generatedPassword: String = "",
    onClickSavePassword: (String) -> Unit,
    onClickCopy: (String) -> Unit,
    onDismissDialogAtLeast: () -> Unit,
    onDismissSavedDialog: () -> Unit,
    showDialogAtLeast: Boolean = false,
    isSaved: Boolean = false,
    isShowSaved: Boolean = false,
    layoutVersion: String?,
) {
    val localFocusManager = LocalFocusManager.current
    val titleActionDialogAtLeast = stringResource(Res.string.home_screen_ok_dialog).uppercase()
    Column(
        modifier = modifier
            .systemBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Column {
                CupertinoBorderedTextField(
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                    value = lengthPassword,
                    onValueChange = onValueLengthChange,
                    placeholder = {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(Res.string.password_length),
                                fontSize = 16.sp,
                                color = Color.Gray.copy(0.8f)
                            )
                        }
                    },
                    isError = isErrorField,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )
                if (isErrorField) {
                    Text(
                        text = stringResource(Res.string.input_error_message),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.lower_text_label),
                    style = MaterialTheme.typography.h6
                )
                AdaptiveSwitch(
                    checked = includeLowercase,
                    onCheckedChange = actionIncludeLowercase,
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.upper_text_label),
                    style = MaterialTheme.typography.h6
                )
                AdaptiveSwitch(
                    checked = includeUppercase,
                    onCheckedChange = actionIncludeUppercase,
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.number_label),
                    style = MaterialTheme.typography.h6
                )
                AdaptiveSwitch(
                    checked = includeDigits,
                    onCheckedChange = actionIncludeDigits,
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.special_char),
                    style = MaterialTheme.typography.h6
                )
                AdaptiveSwitch(
                    checked = includeSpecialChars,
                    onCheckedChange = actionIncludeSpecialChars,
                )
            }
        }

        AdaptiveButton(onClick = onClickGeneratePassword) {
            Icon(
                Icons.Filled.Check,
                contentDescription = stringResource(Res.string.generate_password_title_button)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(Res.string.generate_password_title_button))
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .weight(1f),
            visible = generatedPassword.isNotBlank()
        ) {
            AdaptiveButton(
                modifier = Modifier,
                onClick = {
                    onClickCopy(generatedPassword)
                },
                adaptation = {
                    this.cupertino {
                        this.shape = RoundedCornerShape(20.dp)
                        this.colors = CupertinoButtonDefaults.plainButtonColors(
                            containerColor = Color.LightGray.copy(0.2f)
                        )
                    }
                }
            ) {
                // Display the generated password (consider security implications)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        text = generatedPassword,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 48.sp
                    )
                }
            }
        }

        if (generatedPassword.isNotBlank()) {
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AdaptiveButton(
                    modifier = Modifier
                        .fillMaxHeight(),
                    onClick = {
                        onClickSavePassword(generatedPassword)
                    },
                    enabled = !isSaved
                ) {
                    CupertinoIcon(
                        painter = painterResource(Res.drawable.img_download),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                if (layoutVersion == "v1"){
                    AdaptiveButton(
                        modifier = Modifier
                            .fillMaxHeight(),
                        onClick = { onClickCopy(generatedPassword) }) {
                        CupertinoIcon(
                            painter = painterResource(Res.drawable.img_copy),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }

    if (showDialogAtLeast) {
        AdaptiveAlertDialogNative(
            onDismissRequest = onDismissDialogAtLeast,
            title = stringResource(Res.string.saved_title_dialog_delete),
            message = stringResource(Res.string.home_screen_message_dialog_delete),
            buttons = {
                action(
                    onClick = onDismissDialogAtLeast,
                    title = titleActionDialogAtLeast,
                    style = AlertActionStyle.Cancel
                )
            }
        )
    }

    DialogOk(
        title = stringResource(Res.string.saved),
        isShow = isShowSaved,
        onDismissRequest = onDismissSavedDialog
    )

}