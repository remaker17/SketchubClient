package dev.remaker.sketchubx.util

import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import dev.remaker.sketchubx.*
import dev.remaker.sketchubx.extensions.getStringOrDefault
import dev.remaker.sketchubx.util.theme.ThemeMode

object PreferenceUtil {
    private val sharedPreferences = AppLoader.getContext().getSharedPreferences("dev.remaker.sketchubx_preferences", Context.MODE_PRIVATE)

    fun registerOnSharedPreferenceChangedListener(
        listener: OnSharedPreferenceChangeListener
    ) = sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

    fun unregisterOnSharedPreferenceChangedListener(
        changeListener: OnSharedPreferenceChangeListener
    ) = sharedPreferences.unregisterOnSharedPreferenceChangeListener(changeListener)

    val baseTheme get() = sharedPreferences.getStringOrDefault(GENERAL_THEME, "auto")

    fun getGeneralThemeValue(isSystemDark: Boolean): ThemeMode {
        val themeMode: String =
            sharedPreferences.getStringOrDefault(GENERAL_THEME, "auto")
        return if (isBlackMode && isSystemDark && themeMode != "light") {
            ThemeMode.BLACK
        } else {
            if (isBlackMode && themeMode == "dark") {
                ThemeMode.BLACK
            } else {
                when (themeMode) {
                    "light" -> ThemeMode.LIGHT
                    "dark" -> ThemeMode.DARK
                    "auto" -> ThemeMode.AUTO
                    else -> ThemeMode.AUTO
                }
            }
        }
    }

    val languageCode: String get() = sharedPreferences.getString(LANGUAGE_NAME, "auto") ?: "auto"

    private val isBlackMode
        get() = sharedPreferences.getBoolean(
            BLACK_THEME,
            false
        )

    fun themeResFromPrefValue(themePrefValue: String): Int {
        return when (themePrefValue) {
            "light" -> R.style.Theme_Sketchub_Light
            "dark" -> R.style.Theme_Sketchub
            else -> R.style.Theme_Sketchub
        }
    }
}
