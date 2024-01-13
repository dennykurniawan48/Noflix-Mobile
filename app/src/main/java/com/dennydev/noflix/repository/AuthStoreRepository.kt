package com.dennydev.noflix.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "auth")
@ViewModelScoped
class AuthStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val flowToken: Flow<String> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[tokenKey] ?: ""
        }

    suspend fun saveToken(token: String) {
        dataStore.edit { settings ->
            settings[tokenKey] = token
        }
    }

    suspend fun removeToken(){
        dataStore.edit {
            it[tokenKey] = ""
        }
    }

    companion object PreferencesKey{
        val tokenKey = stringPreferencesKey("_token")
    }
}