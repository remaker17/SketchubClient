package dev.remaker.sketchubx.util.theme

import android.content.Context
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDelegate
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.extensions.generalThemeValue

@StyleRes
fun Context.getThemeResValue(): Int =
    when (generalThemeValue) {
        ThemeMode.LIGHT -> R.style.Theme_Sketchub_Light
        ThemeMode.DARK -> R.style.Theme_Sketchub_Dark
        ThemeMode.BLACK -> R.style.Theme_Sketchub_Black
        ThemeMode.AUTO -> R.style.Theme_Sketchub
    }

fun Context.getNightMode(): Int = when (generalThemeValue) {
    ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
    ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
}
