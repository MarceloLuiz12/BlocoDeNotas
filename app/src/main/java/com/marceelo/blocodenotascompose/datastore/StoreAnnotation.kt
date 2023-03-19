package com.marceelo.blocodenotascompose.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreAnnotation(val context: Context) {

    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("configuration")
        val ANNOTATION_KEY = stringPreferencesKey("annotation")
    }

    val getAnnotation: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ANNOTATION_KEY] ?: ""
    }

    suspend fun saveAnnotation(annotation: String){
        context.dataStore.edit { preferences ->
            preferences[ANNOTATION_KEY] = annotation
        }
    }

}