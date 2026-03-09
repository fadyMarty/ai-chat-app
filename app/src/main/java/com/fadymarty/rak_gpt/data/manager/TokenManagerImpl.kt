package com.fadymarty.rak_gpt.data.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fadymarty.rak_gpt.domain.manager.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManagerImpl(
    private val dataStore: DataStore<Preferences>,
) : TokenManager {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    override suspend fun deleteAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }
}