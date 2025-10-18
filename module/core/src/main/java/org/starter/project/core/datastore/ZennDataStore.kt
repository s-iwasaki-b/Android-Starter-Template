package org.starter.project.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface ZennDataStore {
    val dataStore: DataStore<Preferences>
}

@Singleton
class ZennDataStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : ZennDataStore {
    override val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile("zenn")
        }
    )
}

@Singleton
class ZennPreferencesKey @Inject constructor() {
    val LAST_KEYWORD = stringPreferencesKey("last_keyword")
}
