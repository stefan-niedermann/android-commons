package it.niedermann.android.sharedpreferences

import android.content.Context
import androidx.annotation.DimenRes
import androidx.annotation.Px

object DimensionUtil {
    @Px
    fun dpToPx(context: Context, @DimenRes resource: Int): Int {
        return context.resources.getDimensionPixelSize(resource)
    }
}