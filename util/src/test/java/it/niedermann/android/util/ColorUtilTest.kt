package it.niedermann.android.util

import android.graphics.Color
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.util.Pair
import it.niedermann.android.util.ColorUtil.formatColorToParsableHexString
import it.niedermann.android.util.ColorUtil.getForegroundColorForBackgroundColor
import it.niedermann.android.util.ColorUtil.intColorToHexString
import it.niedermann.android.util.ColorUtil.isColorDark
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ColorUtilTest {
    @Test
    fun testGetForegroundColorForBackgroundColor() {
        for (@ColorInt color in DARK_COLORS) {
            assertEquals(
                    "Expect foreground color for " + String.format("#%06X", 0xFFFFFF and color) + " to be " + String.format("#%06X", 0xFFFFFF and Color.WHITE),
                    Color.WHITE.toLong(), getForegroundColorForBackgroundColor(color)
                    .toLong())
        }
        for (@ColorInt color in LIGHT_COLORS) {
            assertEquals(
                    "Expect foreground color for " + String.format("#%06X", 0xFFFFFF and color) + " to be " + String.format("#%06X", 0xFFFFFF and Color.BLACK),
                    Color.BLACK.toLong(), getForegroundColorForBackgroundColor(color)
                    .toLong())
        }
        assertEquals(
                "Expect foreground color for " + String.format("#%06X", 0xFFFFFF and Color.TRANSPARENT) + " to be " + String.format("#%06X", 0xFFFFFF and Color.BLACK),
                Color.BLACK.toLong(), getForegroundColorForBackgroundColor(Color.TRANSPARENT)
                .toLong())
    }

    @Test
    fun testIsColorDark() {
        for (@ColorInt color in DARK_COLORS) {
            assertTrue(
                    "Expect " + String.format("#%06X", 0xFFFFFF and color) + " to be a dark color",
                    isColorDark(color)
            )
        }
        for (@ColorInt color in LIGHT_COLORS) {
            assertFalse(
                    "Expect " + String.format("#%06X", 0xFFFFFF and color) + " to be a light color",
                    isColorDark(color)
            )
        }
    }

    @Test
    fun testIntColorToHexString() {
        assertEquals("ffffff", intColorToHexString(Color.WHITE));
        assertEquals("000000", intColorToHexString(Color.BLACK));
        assertEquals("0082c9", intColorToHexString(Color.parseColor("#0082C9")));
    }

    @Rule
    @JvmField
    val exception = ExpectedException.none()

    @Test
    fun testGetCleanHexaColorString() {
        val validColors: MutableList<Pair<String, String>> = ArrayList()
        validColors.add(Pair("#0082C9", "#0082C9"))
        validColors.add(Pair("0082C9", "#0082C9"))
        validColors.add(Pair("#CCC", "#CCCCCC"))
        validColors.add(Pair("ccc", "#cccccc"))
        validColors.add(Pair("af0", "#aaff00"))
        validColors.add(Pair("#af0", "#aaff00"))
        // Strip alpha channel (https://github.com/stefan-niedermann/nextcloud-deck/issues/674)
        validColors.add(Pair("af05", "#aaff00"))
        validColors.add(Pair("#af05", "#aaff00"))
        validColors.add(Pair("aaff0055", "#aaff00"))
        validColors.add(Pair("#aaff0055", "#aaff00"))
        for (color in validColors) {
            assertEquals("Expect " + color.first + " to be cleaned up to " + color.second, color.second, formatColorToParsableHexString(color.first))
        }
        val invalidColors = arrayOf(null, "", "cc", "c", "#a", "#55L", "55L")
        for (color in invalidColors) {
            exception.expect(IllegalArgumentException::class.java)
            formatColorToParsableHexString(color)
        }
    }

    companion object {
        @ColorInt
        private val DARK_COLORS = intArrayOf(
                Color.BLACK,
                Color.parseColor("#0082C9"),
                Color.parseColor("#007676")
        )

        @ColorInt
        private val LIGHT_COLORS = intArrayOf(
                Color.WHITE,
                Color.YELLOW
        )
    }
}