package com.example.uts.data

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginData (context: Context){
    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object{
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
        val USER_PASS_KEY = preferencesKey<String>("USER_PASS")
        val USER_GIT_KEY = preferencesKey<String>("USER_GIT")
        val USER_NIM_KEY = preferencesKey<String>("USER_NIM")
        val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL")
        val USER_LOGIN_KEY = preferencesKey<Boolean>("USER_LOGIN")
    }

    suspend fun storeUser(name:String, pass:String, git:String, nim:String, email:String){
        dataStore.edit {
            it[USER_NAME_KEY] = name
            it[USER_PASS_KEY] = pass
            it[USER_GIT_KEY] = git
            it[USER_NIM_KEY]= nim
            it[USER_EMAIL_KEY] = email
        }
    }

    suspend fun storeLogin(login:Boolean){
        dataStore.edit {
            it[USER_LOGIN_KEY] = login
        }
    }

    val userNameFlow : Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    val userPassFlow : Flow<String> = dataStore.data.map {
        it[USER_PASS_KEY] ?: ""
    }

    val userGitFlow : Flow<String> = dataStore.data.map {
        it[USER_GIT_KEY] ?: ""
    }

    val userNimFlow : Flow<String> = dataStore.data.map {
        it[USER_NIM_KEY] ?: ""
    }

    val userEmailFlow : Flow<String> = dataStore.data.map {
        it[USER_EMAIL_KEY] ?: ""
    }

    val userLoginFlow : Flow<Boolean> = dataStore.data.map {
        it[USER_LOGIN_KEY] ?: false
    }
}