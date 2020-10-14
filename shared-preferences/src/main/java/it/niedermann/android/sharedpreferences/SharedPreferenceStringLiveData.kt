package it.niedermann.android.sharedpreferences

import android.content.SharedPreferences

/**
 * https://stackoverflow.com/a/57074217
 */
class SharedPreferenceStringLiveData(prefs: SharedPreferences, key: String, defValue: String) : SharedPreferenceLiveData<String>(prefs, key, defValue) {
    override fun getValueFromPreferences(key: String?, defValue: String): String? {
        return sharedPrefs.getString(key, defValue)
    }
}