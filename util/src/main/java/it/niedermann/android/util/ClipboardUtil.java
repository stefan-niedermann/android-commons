package it.niedermann.android.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ClipboardUtil {

    private static final String TAG = ClipboardUtil.class.getSimpleName();

    private ClipboardUtil() {
    }

    public static boolean copyToClipboard(@NonNull Context context, @Nullable String text) {
        return copyToClipboard(context, text, text);
    }

    public static boolean copyToClipboard(@NonNull Context context, @Nullable String label, @Nullable String text) {
        final ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager == null) {
            Log.e(TAG, "ClipboardManager is null");
            Toast.makeText(context, R.string.could_not_copy_to_clipboard, Toast.LENGTH_LONG).show();
            return false;
        }
        final ClipData clipData = ClipData.newPlainText(label, text);
        clipboardManager.setPrimaryClip(clipData);
        Log.i(TAG, "Copied to clipboard: [" + label + "] \"" + text + "\"");
        Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show();
        return true;
    }
}
