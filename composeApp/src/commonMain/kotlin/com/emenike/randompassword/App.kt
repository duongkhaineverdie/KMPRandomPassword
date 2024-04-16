package com.emenike.randompassword

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.emenike.randompassword.screens.randompassword.RandomPasswordScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(RandomPasswordScreen)
    }
}
