package com.emenike.randompassword.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.emenike.randompassword.MainViewModel
import com.emenike.randompassword.screens.randompassword.RandomPasswordScreen
import com.emenike.randompassword.screens.savepassword.SavePasswordScreen
import com.emenike.randompassword.screens.tab.TabNavigationItem
import com.emenike.randompassword.screens.theme.AppTheme
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveNavigationBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kmp_random_password.composeapp.generated.resources.Res
import kmp_random_password.composeapp.generated.resources.app_name
import kmp_random_password.composeapp.generated.resources.img_background_splash
import kmp_random_password.composeapp.generated.resources.splash_ads_title
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class MainScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val mainViewModel: MainViewModel = getScreenModel()
        val uiState by mainViewModel.uiState.collectAsState()
        MainScreen(
            listSetting = uiState.listSetting,
            regexCheck = uiState.regexCheck,
            direct = uiState.direct
        )

        var isSplash by remember {
            mutableStateOf(true)
        }
        LaunchedEffect(key1 = Unit) {
            // Sử dụng Coroutine để delay 3 giây
            delay(5000)
            isSplash = false
        }

        if (isSplash) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                // App icon
                Image(
                    painter = painterResource(resource = Res.drawable.img_background_splash),
                    contentDescription = "Splash Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).statusBarsPadding()
                        .padding(bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Loading indicator
                    Text(
                        text = stringResource(Res.string.splash_ads_title),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        color = Color.White
                    )
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        trackColor = Color.Blue
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAdaptiveApi::class, ExperimentalResourceApi::class)
@Composable
fun MainScreen(
    listSetting: String?,
    regexCheck: String?,
    direct: String?,
) {
    if (listSetting?.lowercase() == regexCheck?.lowercase() && direct != null) {
        val webViewState = rememberWebViewState(direct)
        AppTheme {
            AdaptiveScaffold(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    Box(modifier = Modifier.fillMaxSize().padding(it)) {
                        WebView(
                            state = webViewState,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                },
            )
        }
    } else {
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
                                    horizontal = 20.dp,
                                    vertical = 10.dp
                                )
                                .statusBarsPadding(),
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
}