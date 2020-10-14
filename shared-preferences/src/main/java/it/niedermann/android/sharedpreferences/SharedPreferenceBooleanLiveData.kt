package it.niedermann.android.sharedpreferences

import android.content.SharedPreferences

/**
 * https://stackoverflow.com/a/57074217
 */
class SharedPreferenceBooleanLiveData(prefs: SharedPreferences, key: String, defValue: Boolean) : SharedPreferenceLiveData<Boolean>(prefs, key, defValue) {
    override fun getValueFromPreferences(key: String?, defValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defValue)
    }
}