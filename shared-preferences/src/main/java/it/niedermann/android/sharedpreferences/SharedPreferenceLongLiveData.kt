package it.niedermann.android.sharedpreferences

import android.content.SharedPreferences

/**
 * https://stackoverflow.com/a/57074217
 */
class SharedPreferenceLongLiveData(prefs: SharedPreferences, key: String, defValue: Long) : SharedPreferenceLiveData<Long>(prefs, key, defValue) {
    override fun getValueFromPreferences(key: String?, defValue: Long): Long {
        return sharedPrefs.getLong(key, defValue)
    }
}