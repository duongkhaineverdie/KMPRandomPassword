package com.emenike.randompassword.data.factory

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import com.emenike.randompassword.data.factory.SETTINGS_PREFERENCES
import com.emenike.randompassword.data.factory.createDataStoreWithDefaults
import io.kamel.core.utils.File
import kotlinx.coroutines.CoroutineScope

fun dataStorePreferences(
    context: Context
): DataStore<Preferences> = createDataStoreWithDefaults(
    path = {
        File(context.filesDir, "datastore/$SETTINGS_PREFERENCES").path
    }
)