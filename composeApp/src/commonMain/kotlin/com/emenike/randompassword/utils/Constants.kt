package com.emenike.randompassword.utils

import androidx.datastore.preferences.core.stringSetPreferencesKey

object Constants {
    const val LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz"
    val UPPERCASE_LETTERS = LOWERCASE_LETTERS.uppercase()
    const val DIGITS = "0123456789"
    const val SPECIAL_CHAR = "!@#$%^&*()_+-=[]{}|;:'<>,.?/"

    val PASSWORD_CREATED = stringSetPreferencesKey("PASSWORD_CREATED")
}

