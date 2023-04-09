package com.fangga.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(private val context: Context) {
    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreUtil.DATA_STORE_NAME
    )

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        context.userPreferenceDataStore.edit {
            it[DataStoreUtil.DATA_STORE_PREF_THEME_KEY] = isDarkModeActive
        }
    }

    fun getThemeSetting(): Flow<Boolean> =
        context.userPreferenceDataStore.data.map {
            it[DataStoreUtil.DATA_STORE_PREF_THEME_KEY] ?: false
        }
}