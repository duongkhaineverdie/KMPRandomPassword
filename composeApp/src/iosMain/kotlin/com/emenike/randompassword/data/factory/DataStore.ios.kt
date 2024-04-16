package com.emenike.randompassword.data.factory

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.emenike.randompassword.data.factory.SETTINGS_PREFERENCES
import com.emenike.randompassword.data.factory.createDataStoreWithDefaults
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun dataStorePreferences(): DataStore<Preferences> = createDataStoreWithDefaults(
    path = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        (requireNotNull(documentDirectory).path + "/$SETTINGS_PREFERENCES")
    }
)