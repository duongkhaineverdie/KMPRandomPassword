package com.emenike.randompassword.screens.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.alexzhirkevich.cupertino.AlertActionStyle
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveAlertDialogNative
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kmp_random_password.composeapp.generated.resources.Res
import kmp_random_password.composeapp.generated.resources.home_screen_ok_dialog
import kmp_random_password.composeapp.generated.resources.saved_title_dialog_delete
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalAdaptiveApi::class, ExperimentalResourceApi::class)
@Composable
fun DialogOk(
    isShow: Boolean = false,
    onDismissRequest: () -> Unit = {/* no-op */},
    title: String,
) {
    val titleActionOk = stringResource(Res.string.home_screen_ok_dialog)
    if (isShow){
        AdaptiveAlertDialogNative(
            onDismissRequest = onDismissRequest,
            title = title,
            message = "",
            buttons = {
                action(
                    onClick = onDismissRequest,
                    style = AlertActionStyle.Default,
                    title = titleActionOk
                )
            }
        )
    }
}