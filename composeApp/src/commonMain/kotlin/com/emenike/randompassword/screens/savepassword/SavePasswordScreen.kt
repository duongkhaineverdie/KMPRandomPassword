package com.emenike.randompassword.screens.savepassword

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import com.emenike.randompassword.screens.tab.SavedTab
import io.github.alexzhirkevich.LocalContentColor
import io.github.alexzhirkevich.cupertino.AlertActionStyle
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.CupertinoHorizontalDivider
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveAlertDialog
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveAlertDialogNative
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveIconButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kmp_random_password.composeapp.generated.resources.Res
import kmp_random_password.composeapp.generated.resources.copied
import kmp_random_password.composeapp.generated.resources.empty_list
import kmp_random_password.composeapp.generated.resources.icon_delete
import kmp_random_password.composeapp.generated.resources.icon_menu
import kmp_random_password.composeapp.generated.resources.icon_saved
import kmp_random_password.composeapp.generated.resources.icon_selected
import kmp_random_password.composeapp.generated.resources.icon_unselected
import kmp_random_password.composeapp.generated.resources.img_copy
import kmp_random_password.composeapp.generated.resources.list_tab
import kmp_random_password.composeapp.generated.resources.saved_cancel_dialog_delete
import kmp_random_password.composeapp.generated.resources.saved_message_dialog_delete
import kmp_random_password.composeapp.generated.resources.saved_remove_dialog_delete
import kmp_random_password.composeapp.generated.resources.saved_title_dialog_delete
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data object SavePasswordScreen : SavedTab {
    @OptIn(ExperimentalAdaptiveApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val savedPasswordViewModel: SavedPasswordViewModel = getScreenModel()
        val uiState by savedPasswordViewModel.uiState.collectAsState()

        val clipboardManager = LocalClipboardManager.current
        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }
        AdaptiveScaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) {
            SavePasswordScreen(
                modifier = Modifier.fillMaxSize(),
                passwords = uiState.passwords,
                onDeletePassword = savedPasswordViewModel::deletePasswords,
                onSelectedPasswordDelete = savedPasswordViewModel::selectedPasswordDelete,
                passwordsDelete = uiState.passwordsDelete,
                isDeleteMode = uiState.isDeleteMode,
                onActionDeleteMode = savedPasswordViewModel::actionDeleteMode,
                onClickItem = {
                    clipboardManager.setText(AnnotatedString(it))
                    scope.launch {
                        snackBarHostState.showSnackbar(getString(Res.string.copied))
                    }
                }
            )
        }
    }

}

@OptIn(
    ExperimentalAdaptiveApi::class, ExperimentalResourceApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SavePasswordScreen(
    modifier: Modifier = Modifier,
    passwords: List<String> = arrayListOf(),
    passwordsDelete: List<String> = arrayListOf(),
    isDeleteMode: Boolean = true,
    onDeletePassword: (List<String>) -> Unit,
    onSelectedPasswordDelete: (String) -> Unit,
    onActionDeleteMode: () -> Unit,
    onClickItem: (String) -> Unit,
) {
    var showDialogDelete by remember { mutableStateOf(false) }
    val cancelText = stringResource(Res.string.saved_cancel_dialog_delete)
    val removeText = stringResource(Res.string.saved_remove_dialog_delete)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (passwords.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AdaptiveIconButton(
                    onClick = {
                        if (isDeleteMode && passwordsDelete.isNotEmpty()) {
                            showDialogDelete = true
                        } else {
                            onActionDeleteMode()
                        }
                    }
                ) {
                    CupertinoIcon(
                        modifier = Modifier.size(24.dp),
                        painter = if (isDeleteMode && passwordsDelete.isNotEmpty())
                            painterResource(Res.drawable.icon_delete)
                        else
                            painterResource(
                                Res.drawable.icon_menu
                            ),
                        contentDescription = null
                    )
                }

                if (isDeleteMode) {
                    AdaptiveButton(
                        onClick = {
                            onActionDeleteMode()
                        },
                        adaptation = {
                            this.cupertino {
                                this.colors = CupertinoButtonDefaults.plainButtonColors(
                                    containerColor = Color.Transparent,
                                )
                            }
                        }
                    ) {
                        CupertinoText(stringResource(Res.string.saved_cancel_dialog_delete))
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Color.LightGray.copy(0.3f)
                    )
            ) {
                items(passwords.reversed(), key = { it }) {
                    AdaptiveButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .animateItemPlacement(),
                        onClick = {
                            if (isDeleteMode) onSelectedPasswordDelete(it) else onClickItem(
                                it
                            )
                        },
                        adaptation = {
                            this.cupertino {
                                this.shape = RectangleShape
                                this.colors = CupertinoButtonDefaults.filledButtonColors(
                                    containerColor = Color.Transparent
                                )
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedVisibility(isDeleteMode) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = if (passwordsDelete.contains(it))
                                        painterResource(Res.drawable.icon_selected)
                                    else painterResource(
                                        Res.drawable.icon_unselected
                                    ),
                                    contentDescription = null,
                                )
                            }
                            Text(
                                modifier = Modifier.padding(horizontal = 30.dp),
                                fontSize = 24.sp,
                                text = it,
                            )
                        }
                    }
                    CupertinoHorizontalDivider()
                }
            }
        } else {
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(Res.string.empty_list),
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    if (showDialogDelete) {
        AdaptiveAlertDialogNative(
            onDismissRequest = { showDialogDelete = false },
            title = stringResource(Res.string.saved_title_dialog_delete),
            message = "${stringResource(Res.string.saved_message_dialog_delete)} ${passwordsDelete.size}",
            buttons = {
                action(
                    onClick = { showDialogDelete = false },
                    title = cancelText,
                    style = AlertActionStyle.Cancel
                )
                action(
                    onClick = { onDeletePassword(passwordsDelete) },
                    title = removeText,
                    style = AlertActionStyle.Destructive
                )
            }
        )
    }

}
