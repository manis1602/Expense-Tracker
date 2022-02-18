package com.manikandan.expensetracker.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.manikandan.expensetracker.domain.model.DataStoreUserCredentials
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.repository.DataStoreOperations
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_LOGIN_COMPLETED_KEY
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_NAME
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_ON_BOARDING_COMPLETED_KEY
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_USER_EMAIL_KEY
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_USER_GENDER_KEY
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_USER_ID_KEY
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_USER_NAME_KEY
import com.manikandan.expensetracker.utils.Constants.PREFERENCES_USER_PASSWORD_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.IOException

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context): DataStoreOperations {

    private object PreferencesKeys {
        val onBoardingCompletedKey = booleanPreferencesKey(PREFERENCES_ON_BOARDING_COMPLETED_KEY)
        val loginStateKey = booleanPreferencesKey(PREFERENCES_LOGIN_COMPLETED_KEY)
        val userIdKey = stringPreferencesKey(PREFERENCES_USER_ID_KEY)
        val userNameKey = stringPreferencesKey(PREFERENCES_USER_NAME_KEY)
        val userEmailKey = stringPreferencesKey(PREFERENCES_USER_EMAIL_KEY)
        val userPasswordKey = stringPreferencesKey(PREFERENCES_USER_PASSWORD_KEY)
        val userGenderKey = stringPreferencesKey(PREFERENCES_USER_GENDER_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.onBoardingCompletedKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emptyPreferences()
                } else {
                    throw exception
                }
            }.map { preferences ->
                val onBoardingCompleted =
                    preferences[PreferencesKeys.onBoardingCompletedKey] ?: false
                onBoardingCompleted
            }
    }

    override suspend fun saveLoginState(isLoginCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.loginStateKey] = isLoginCompleted

        }
    }

    override fun readLoginState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
            if (exception is IOException){
                emptyPreferences()
            }else{
                throw exception
            }

            }.map { preferences ->
                val loginState = preferences[PreferencesKeys.loginStateKey] ?: false
                loginState
            }
    }

    override suspend fun saveUserCredentials(
        user:User
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.userIdKey] = user.userId
            preferences[PreferencesKeys.userNameKey] = user.userName
            preferences[PreferencesKeys.userEmailKey] = user.emailAddress
            preferences[PreferencesKeys.userPasswordKey] = user.password
            preferences[PreferencesKeys.userGenderKey] = user.gender
        }
    }

    override suspend fun readUserCredentials(): User {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emptyPreferences()
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val userId = preferences[PreferencesKeys.userIdKey] ?: ""
                val userName = preferences[PreferencesKeys.userNameKey] ?: ""
                val userEmail = preferences[PreferencesKeys.userEmailKey] ?: ""
                val userPassword = preferences[PreferencesKeys.userPasswordKey] ?: ""
                val userGender = preferences[PreferencesKeys.userGenderKey] ?: ""
                User(
                    userId = userId,
                    userName = userName,
                    emailAddress = userEmail,
                    password = userPassword,
                    gender = userGender
                )
            }.first()
    }
}