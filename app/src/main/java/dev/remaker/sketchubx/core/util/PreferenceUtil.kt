package dev.remaker.sketchubx.core.util

import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import dev.remaker.sketchubx.*
import dev.remaker.sketchubx.extensions.getStringOrDefault

object PreferenceUtil {
    private val sharedPreferences = AppLoader.getContext().getSharedPreferences("dev.remaker.sketchubx_preferences", Context.MODE_PRIVATE)

    fun registerOnSharedPreferenceChangedListener(
        listener: OnSharedPreferenceChangeListener
    ) = sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    fun unregisterOnSharedPreferenceChangedListener(
        changeListener: OnSharedPreferenceChangeListener
    ) = sharedPreferences.unregisterOnSharedPreferenceChangeListener(changeListener)

    val languageCode get() = sharedPreferences.getStringOrDefault(LANGUAGE_NAME, "auto")
}
