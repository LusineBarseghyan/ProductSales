package com.example.quickstartlessons.module.base.utils

object PreferencesManager {

    private const val PREF_KEY_CURRENT_LANGUAGE = "pref_key_current_language"
    private const val PREF_USER_NAME = "pref_key_user_name"
    private const val PREF_USER_PASSWORD = "pref_key_password"

    fun putTCurrentLanguage(languageCode: String) = Prefs.put(PREF_KEY_CURRENT_LANGUAGE, languageCode)
    fun getCurrentLanguage() = Prefs.getString(PREF_KEY_CURRENT_LANGUAGE, "en") ?: "en"


    fun putUserName(userName: String) = Prefs.put(PREF_USER_NAME, userName)
    fun getUserName() = Prefs.getString(PREF_USER_NAME)

    fun putUserPassword(password: String) = Prefs.put(PREF_USER_PASSWORD, password)
    fun getUserPassword() = Prefs.getString(PREF_USER_PASSWORD)

    fun removeUserName() = Prefs.remove(PREF_USER_NAME)
    fun removePassword() = Prefs.remove(PREF_USER_PASSWORD)
}