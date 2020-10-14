package it.niedermann.android.util

import android.content.Context
import androidx.annotation.DimenRes
import androidx.annotation.Px

object DimensionUtil {
    @Px
    fun dpToPx(context: Context, @DimenRes resource: Int): Int {
        return context.resources.getDimensionPixelSize(resource)
    }
}
