package com.yeputra.moviecatalogue.repository.preference

import android.content.Context
import android.content.SharedPreferences

class SettingPref(val context: Context) {

    private var pref: SharedPreferences = context.getSharedPreferences(context.packageName, 0)

    var dailyRemainder: Boolean
        get() = pref.getBoolean(PREF_DAILY_REMAINDER, false)
        set(value) {
            pref.edit().putBoolean(PREF_DAILY_REMAINDER, value).apply()
        }

    var releaseRemainder: Boolean
        get() = pref.getBoolean(PREF_RELEASE_REMAINDER, false)
        set(value) {
            pref.edit().putBoolean(PREF_RELEASE_REMAINDER, value).apply()
        }

    var isRemaindDaily: Boolean
        get() = pref.getBoolean(PREF_REMIND_DAILY, false)
        set(value) {
            pref.edit().putBoolean(PREF_REMIND_DAILY, value).apply()
        }

    var isRemaindRelease: Boolean
        get() = pref.getBoolean(PREF_REMIND_RELEASE, false)
        set(value) {
            pref.edit().putBoolean(PREF_REMIND_RELEASE, value).apply()
        }

    companion object {
        const val PREF_DAILY_REMAINDER = "pref_daily_remainder"
        const val PREF_RELEASE_REMAINDER = "pref_release_remainder"

        const val PREF_REMIND_RELEASE = "pref_remind_daily"
        const val PREF_REMIND_DAILY = "pref_remind_release"
    }
}