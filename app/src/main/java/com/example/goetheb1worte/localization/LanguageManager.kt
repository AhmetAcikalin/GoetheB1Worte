package com.example.goetheb1worte.localization

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageManager(
    context: Context? = null
) {
    private val prefs = context?.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    private val _language = MutableStateFlow(loadInitialLanguage())
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private fun loadInitialLanguage(): AppLanguage {
        val code = prefs?.getString(KEY_LANGUAGE, AppLanguage.SYSTEM.code) ?: AppLanguage.SYSTEM.code
        return AppLanguage.entries.firstOrNull { it.code == code } ?: AppLanguage.SYSTEM
    }

    fun setLanguage(appLanguage: AppLanguage) {
        prefs?.edit()?.putString(KEY_LANGUAGE, appLanguage.code)?.apply()
        _language.value = appLanguage
    }

    companion object {
        private const val KEY_LANGUAGE = "pref_app_language"
    }
}
