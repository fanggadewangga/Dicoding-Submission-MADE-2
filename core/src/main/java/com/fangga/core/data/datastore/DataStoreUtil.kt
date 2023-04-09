package com.fangga.core.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey

object DataStoreUtil {
    const val DATA_STORE_NAME = "USER_DATASTORE"
    val DATA_STORE_PREF_THEME_KEY = booleanPreferencesKey("THEME_KEY")
}