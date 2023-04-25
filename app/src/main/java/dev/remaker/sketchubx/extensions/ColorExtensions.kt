package dev.remaker.sketchubx.extensions

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.util.ColorUtil

/**
 * Returns the color for the given attribute, or fallback if not found.
 */
@JvmOverloads
fun Fragment.resolveColor(@AttrRes attr: Int, @ColorInt fallback: Int = Color.BLACK): Int =
    requireContext().resolveColor(attr, fallback)

/**
 * Returns the color for the given attribute, or fallback if not found.
 */
@JvmOverloads
fun Dialog.resolveColor(@AttrRes attr: Int, @ColorInt fallback: Int = Color.BLACK): Int =
    context.resolveColor(attr, fallback)

/**
 * Returns the color for the given attribute, or fallback if not found.
 */
@JvmOverloads
fun Context.resolveColor(@AttrRes attr: Int, @ColorInt fallback: Int = Color.BLACK): Int =
    theme.obtainStyledAttributes(intArrayOf(attr)).use { typedArray ->
        typedArray.getColor(0, fallback)
    }

/**
 * Tints the drawable with the given color.
 */
@CheckResult
fun Drawable.tint(@ColorInt color: Int): Drawable {
    val tintedDrawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTint(tintedDrawable, color)
    return tintedDrawable
}

/**
 * Tints the drawable with the color from the given color resource.
 */
@CheckResult
fun Drawable.tint(context: Context, @ColorRes color: Int): Drawable =
    tint(context.getColorRes(color))

/**
 * Returns the color for the given color resource.
 */
@ColorInt
fun Context.getColorRes(@ColorRes colorRes: Int): Int =
    ContextCompat.getColor(this, colorRes)

/**
 * Returns the [ColorStateList] for the given color.
 */
inline val @receiver:ColorInt Int.colorStateList: ColorStateList
    get() = ColorStateList.valueOf(this)

/**
 * Returns `true` if the color is light.
 */
inline val @receiver:ColorInt Int.isColorLight: Boolean
    get() = ColorUtil.isColorLight(this)

/**
 * Returns a lighter version of the color.
 */
inline val @receiver:ColorInt Int.lightColor: Int
    get() = ColorUtil.withAlpha(this, 0.5F)

/**
 * Returns a lighter version of the color.
 */
inline val @receiver:ColorInt Int.lighterColor: Int
    get() = ColorUtil.lightenColor(this)

/**
 * Returns a darker version of the color.
 */
inline val @receiver:ColorInt Int.darkerColor: Int
    get() = ColorUtil.darkenColor(this)

/**
 * Returns the color with the specified alpha value.
 */
fun @receiver:ColorInt Int.addAlpha(alpha: Float): Int =
    ColorUtil.withAlpha(this, alpha)