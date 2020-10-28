package it.niedermann.android.util

import android.graphics.Color
import androidx.annotation.ColorInt
import java.util.*

/**
 * Based on https://github.com/LeaVerou/contrast-ratio
 */
object ColorUtil {
    private val FOREGROUND_CACHE: MutableMap<Int, Int> = HashMap()
    private val IS_DARK_COLOR_CACHE: MutableMap<Int, Boolean> = HashMap()
    @ColorInt
    public fun getForegroundColorForBackgroundColor(@ColorInt color: Int): Int {
        var ret = FOREGROUND_CACHE[color]
        if (ret == null) {
            ret = if (Color.TRANSPARENT == color) Color.BLACK else if (isColorDark(color)) Color.WHITE else Color.BLACK
            FOREGROUND_CACHE[color] = ret
        }
        return ret
    }

    public fun isColorDark(@ColorInt color: Int): Boolean {
        var ret = IS_DARK_COLOR_CACHE[color]
        if (ret == null) {
            ret = getBrightness(color) < 200
            IS_DARK_COLOR_CACHE[color] = ret
        }
        return ret
    }

    public fun getBrightness(@ColorInt color: Int): Int {
        val rgb = intArrayOf(Color.red(color), Color.green(color), Color.blue(color))
        return Math.sqrt(rgb[0] * rgb[0] * .241 + (rgb[1]
                * rgb[1] * .691) + rgb[2] * rgb[2] * .068).toInt()
    }

    public fun getContrastRatio(@ColorInt colorOne: Int, @ColorInt colorTwo: Int): Double {
        val lum1 = getLuminanace(colorOne)
        val lum2 = getLuminanace(colorTwo)
        val brightest = Math.max(lum1, lum2)
        val darkest = Math.min(lum1, lum2)
        return (brightest + 0.05) / (darkest + 0.05)
    }

    public fun getLuminanace(@ColorInt color: Int): Double {
        val rgb = intArrayOf(Color.red(color), Color.green(color), Color.blue(color))
        return getSubcolorLuminance(rgb[0]) * 0.2126 + getSubcolorLuminance(rgb[1]) * 0.7152 + getSubcolorLuminance(rgb[2]) * 0.0722
    }

    public fun getSubcolorLuminance(@ColorInt color: Int): Double {
        val value = color / 255.0
        return if (value <= 0.03928) value / 12.92 else Math.pow((value + 0.055) / 1.055, 2.4)
    }


    /**
     * @return well formatted string starting with a hash followed by 6 hex numbers that is parsable by [Color.parseColor].
     */
    public fun formatColorToParsableHexString(input: String?): String? {
        requireNotNull(input) { "input color string is null" }
        if (isParsableValidHexColorString(input)) {
            return input
        }
        val chars = input.replace("#".toRegex(), "").toCharArray()
        val sb = StringBuilder(7).append("#")
        when (chars.size) {
            8 -> {
                // Strip alpha channel
                sb.append(Arrays.copyOfRange(chars, 0, 6))
            }
            6 -> {
                // Default long
                sb.append(chars)
            }
            4 -> {
                // Strip alpha channel
                for (c in Arrays.copyOfRange(chars, 0, 3)) {
                    sb.append(c).append(c)
                }
            }
            3 -> {
                // Default short
                for (c in chars) {
                    sb.append(c).append(c)
                }
            }
            else -> {
                throw IllegalArgumentException("unparsable color string: \"$input\"")
            }
        }
        val formattedHexColor = sb.toString()
        return if (isParsableValidHexColorString(formattedHexColor)) {
            formattedHexColor
        } else {
            throw IllegalArgumentException("\"$input\" is not a valid color string. Result of tried normalizing: $formattedHexColor")
        }
    }

    /**
     * Checking for [android.graphics.Color.parseColor] being able to parse the input is the important part because we don't know the implementation and rely on it to be able to parse the color.
     *
     * @return true, if the input starts with a hash followed by 6 characters of hex numbers and is parsable by [android.graphics.Color.parseColor].
     */
    public fun isParsableValidHexColorString(input: String): Boolean {
        return try {
            Color.parseColor(input)
            input.matches(Regex("#[a-fA-F0-9]{6}"))
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Formats the given [ColorInt] to a 6 digit lowercase string *without* leading # character
     */
    fun intColorToHexString(@ColorInt color: Int): String? {
        return String.format("%06X", 0xFFFFFF and color).toLowerCase(Locale.getDefault());
    }
}