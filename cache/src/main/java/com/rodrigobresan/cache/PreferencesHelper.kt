package com.rodrigobresan.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private val PREF_APP_PACKAGE_NAME = "com.rodrigobresan.boilerplate.preferences"
        private val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREF_APP_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var lastCacheTime: Long
        get() = prefs.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = prefs.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()
}