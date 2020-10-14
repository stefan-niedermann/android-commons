package it.niedermann.android.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import java.net.MalformedURLException
import java.net.URL

object ClipboardUtil {
    private val TAG = ClipboardUtil::class.java.simpleName
    fun copyToClipboard(context: Context, text: String?): Boolean {
        return copyToClipboard(context, text, text)
    }

    fun copyToClipboard(context: Context, label: String?, text: String?): Boolean {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        if (clipboardManager == null) {
            Log.e(TAG, "ClipboardManager is null")
            Toast.makeText(context, R.string.could_not_copy_to_clipboard, Toast.LENGTH_LONG).show()
            return false
        }
        val clipData = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clipData)
        Log.i(TAG, "Copied to clipboard: [$label] \"$text\"")
        Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
        return true
    }

    /**
     * @param context [Context]
     * @return `null` if the clipboard does not contain a valid [URL], otherwise the [URL] as [String].
     */
    fun getClipboardURLorNull(context: Context): String? {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                ?: return null
        val clipboardData = clipboardManager.primaryClip ?: return null
        if (clipboardData.itemCount < 1) {
            return null
        }
        val clipItem = clipboardData.getItemAt(0) ?: return null
        val clipText = clipItem.text
        if (TextUtils.isEmpty(clipText)) {
            return null
        }
        try {
            return URL(clipText.toString()).toString()
        } catch (e: MalformedURLException) {
            Log.d(TAG, "Clipboard does not contain a valid URL: $clipText")
        }
        return null
    }
}
