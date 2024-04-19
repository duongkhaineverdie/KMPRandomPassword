package com.emenike.randompassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.emenike.randompassword.screens.randompassword.RandomPasswordScreen
import com.emenike.randompassword.screens.savepassword.SavePasswordScreen
import com.emenike.randompassword.screens.tab.TabNavigationItem
import com.emenike.randompassword.screens.theme.AppTheme
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveNavigationBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kmp_random_password.composeapp.generated.resources.Res
import kmp_random_password.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class,
    ExperimentalAdaptiveApi::class
)
@Composable
fun App() {
    AppTheme {
        TabNavigator(RandomPasswordScreen) {
            AdaptiveScaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(0.2f)),
                content = {
                    Box(modifier = Modifier.fillMaxSize().padding(it)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    AdaptiveNavigationBar {
                        TabNavigationItem(RandomPasswordScreen)
                        TabNavigationItem(SavePasswordScreen)
                    }
                },
                topBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 10.dp,
                                horizontal = 20.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.app_name),
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                }
            )
        }
    }
}
