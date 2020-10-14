package it.niedermann.android.sharedpreferences

import android.content.SharedPreferences

/**
 * https://stackoverflow.com/a/57074217
 */
class SharedPreferenceFloatLiveData(prefs: SharedPreferences, key: String, defValue: Float) : SharedPreferenceLiveData<Float>(prefs, key, defValue) {
    override fun getValueFromPreferences(key: String?, defValue: Float): Float {
        return sharedPrefs.getFloat(key, defValue)
    }
}