package it.niedermann.android.sharedpreferences

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData

/**
 * https://stackoverflow.com/a/57074217
 */
abstract class SharedPreferenceLiveData<T>(var sharedPrefs: SharedPreferences, var key: String, var defValue: T) : LiveData<T>() {
    private val preferenceChangeListener = OnSharedPreferenceChangeListener { _, key ->
        if (this@SharedPreferenceLiveData.key == key) {
            value = getValueFromPreferences(key, defValue)
        }
    }

    abstract fun getValueFromPreferences(key: String?, defValue: T): T
    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}