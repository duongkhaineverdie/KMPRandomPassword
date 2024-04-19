package com.emenike.randompassword.screens.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kmp_random_password.composeapp.generated.resources.Res
import kmp_random_password.composeapp.generated.resources.copied
import kmp_random_password.composeapp.generated.resources.home_tab
import kmp_random_password.composeapp.generated.resources.icon_home
import kmp_random_password.composeapp.generated.resources.icon_saved
import kmp_random_password.composeapp.generated.resources.img_copy
import kmp_random_password.composeapp.generated.resources.save_password
import kmp_random_password.composeapp.generated.resources.save_tab
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

interface SavedTab : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.save_tab)
            val icon = painterResource(Res.drawable.icon_saved)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}