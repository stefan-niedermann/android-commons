package it.niedermann.android.sharedpreferences

import android.content.SharedPreferences

/**
 * https://stackoverflow.com/a/57074217
 */
class SharedPreferenceIntLiveData(prefs: SharedPreferences, key: String, defValue: Int) : SharedPreferenceLiveData<Int>(prefs, key, defValue) {
    override fun getValueFromPreferences(key: String?, defValue: Int): Int {
        return sharedPrefs.getInt(key, defValue)
    }
}