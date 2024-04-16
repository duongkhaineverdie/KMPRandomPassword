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
    context: Context,
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>,
): DataStore<Preferences> = createDataStoreWithDefaults(
    corruptionHandler = corruptionHandler,
    migrations = migrations,
    coroutineScope = coroutineScope,
    path = {
        File(context.filesDir, "datastore/$SETTINGS_PREFERENCES").path
    }
)