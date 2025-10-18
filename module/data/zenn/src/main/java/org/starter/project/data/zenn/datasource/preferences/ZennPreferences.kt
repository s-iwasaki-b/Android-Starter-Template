package org.starter.project.data.zenn.datasource.preferences

import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.starter.project.core.datastore.ZennDataStore
import org.starter.project.core.datastore.ZennPreferencesKey
import javax.inject.Inject

interface ZennPreferences {
    val lastKeyword: Flow<String>
    suspend fun updateLastKeyword(keyword: String)
}

class ZennPreferencesImpl @Inject constructor(
    private val zennDataStore: ZennDataStore,
    private val zennPreferencesKey: ZennPreferencesKey
) : ZennPreferences {
    override var lastKeyword: Flow<String> = zennDataStore.dataStore.data.map {
        it[zennPreferencesKey.LAST_KEYWORD] ?: ""
    }

    override suspend fun updateLastKeyword(keyword: String) {
        zennDataStore.dataStore.edit {
            it[zennPreferencesKey.LAST_KEYWORD] = keyword
        }
    }
}
