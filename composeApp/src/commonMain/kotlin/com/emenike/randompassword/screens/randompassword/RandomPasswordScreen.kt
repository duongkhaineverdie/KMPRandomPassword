package com.emenike.randompassword.screens.randompassword

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.app_name
import kmp_app_template.composeapp.generated.resources.empty_list
import kmp_app_template.composeapp.generated.resources.generate_password_title_button
import kmp_app_template.composeapp.generated.resources.img_copy
import kmp_app_template.composeapp.generated.resources.img_download
import kmp_app_template.composeapp.generated.resources.input_error_message
import kmp_app_template.composeapp.generated.resources.lower_text_label
import kmp_app_template.composeapp.generated.resources.number_label
import kmp_app_template.composeapp.generated.resources.password_length
import kmp_app_template.composeapp.generated.resources.save_password
import kmp_app_template.composeapp.generated.resources.special_char
import kmp_app_template.composeapp.generated.resources.upper_text_label
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data object RandomPasswordScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val homeViewModel: RandomPasswordViewModel = getScreenModel()

        val uiState by homeViewModel.uiState.collectAsState()

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

            },
            onClickSavePassword = homeViewModel::savePassword,
            passwords = uiState.passwords,
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
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
    onClickCopy: (String) -> Unit,
    onClickSavePassword: (String) -> Unit,
    passwords: MutableList<String> = arrayListOf(),
) {
    val localFocusManager = LocalFocusManager.current

    Column(
        modifier
            .systemBarsPadding()
            .background(Color.Gray.copy(0.2f))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            }
    ) {
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
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    OutlinedTextField(
                        value = lengthPassword,
                        onValueChange = onValueLengthChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(Res.string.password_length), fontSize = 16.sp) },
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
                modifier = Modifier.fillMaxWidth(),
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
                    Checkbox(
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
                    Checkbox(
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
                    Checkbox(
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
                    Checkbox(
                        checked = includeSpecialChars,
                        onCheckedChange = actionIncludeSpecialChars,
                    )
                }
            }

            Button(onClick = onClickGeneratePassword) {
                Icon(Icons.Filled.Check, contentDescription = stringResource(Res.string.generate_password_title_button))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(Res.string.generate_password_title_button))
            }

            if (generatedPassword.isNotBlank()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(),
                            onClick = { onClickSavePassword(generatedPassword) }) {
                            Icon(
                                painter = painterResource(Res.drawable.img_download),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp).padding(5.dp)
                            )
                        }
                        // Display the generated password (consider security implications)
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = generatedPassword,
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 20.sp
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(),
                            onClick = { onClickCopy(generatedPassword) }) {
                            Icon(
                                painter = painterResource(Res.drawable.img_copy),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp).padding(5.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Gray.copy(0.1f))
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = stringResource(Res.string.save_password),
                style = MaterialTheme.typography.h5
            )
            Divider(
                color = Color.Gray.copy(0.5f)
            )
            if (passwords.isNotEmpty()) {
                LazyColumn {
                    items(passwords.reversed()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                fontSize = 18.sp,
                                text = it
                            )
                            Button(
                                modifier = Modifier
                                    .fillMaxHeight(),
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black
                                ),
                                contentPadding = PaddingValues(),
                                onClick = { onClickCopy(generatedPassword) }) {
                                Icon(
                                    painter = painterResource(Res.drawable.img_copy),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp).padding(5.dp)
                                )
                            }
                        }
                        Divider(
                            color = Color.Gray.copy(0.5f)
                        )
                    }
                }
            } else {
                Text(modifier = Modifier.padding(top = 20.dp), text = stringResource(Res.string.empty_list))
            }
        }

    }

}