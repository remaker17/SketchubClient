package dev.remaker.sketchubx.extensions

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.PowerManager
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import dev.remaker.sketchubx.core.util.PreferenceUtil

fun Fragment.getIntRes(@IntegerRes int: Int): Int {
    return resources.getInteger(int)
}

fun Context.getIntRes(@IntegerRes int: Int): Int {
    return resources.getInteger(int)
}

fun Context.isSystemDarkModeEnabled(): Boolean {
    val isBatterySaverEnabled =
        (getSystemService<PowerManager>())?.isPowerSaveMode ?: false
    val isDarkModeEnabled =
        (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    return isBatterySaverEnabled or isDarkModeEnabled
}

inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.let { it.get(key) }
    if (value is T) value else default
}

inline fun <reified T : Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    val value = arguments?.let { it.get(key) }
    requireNotNull(if (value is T) value else default) { key }
}

@Suppress("UNCHECKED_CAST")
fun <T> AppCompatActivity.whichFragment(@IdRes id: Int): T {
    return supportFragmentManager.findFragmentById(id) as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.whichFragment(@IdRes id: Int): T {
    return childFragmentManager.findFragmentById(id) as T
}

fun Fragment.showToast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    showToast(getString(stringRes), duration)
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable {
    return AppCompatResources.getDrawable(this, drawableRes)!!
}

fun Fragment.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable {
    return AppCompatResources.getDrawable(requireContext(), drawableRes)!!
}

fun Fragment.applyToolbar(toolbar: Toolbar) {
    (requireActivity() as AppCompatActivity).applyToolbar(toolbar)
}
