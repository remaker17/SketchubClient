package dev.remaker.sketchubx.extensions

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import dev.remaker.sketchubx.core.util.VersionUtils

fun AppCompatActivity.setDrawBehindSystemBars() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.navigationBarColor = Color.TRANSPARENT
    window.statusBarColor = Color.TRANSPARENT
    if (VersionUtils.hasQ()) {
        window.isNavigationBarContrastEnforced = false
    }
}

@Suppress("Deprecation")
fun AppCompatActivity.setLightStatusBar(enabled: Boolean) {
    if (VersionUtils.hasMarshmallow()) {
        val decorView = window.decorView
        val systemUiVisibility = decorView.systemUiVisibility
        if (enabled) {
            decorView.systemUiVisibility =
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility =
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}

@Suppress("Deprecation")
fun AppCompatActivity.setLightNavigationBar(enabled: Boolean) {
    if (VersionUtils.hasOreo()) {
        val decorView = window.decorView
        var systemUiVisibility = decorView.systemUiVisibility
        if (enabled) {
            decorView.systemUiVisibility =
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            decorView.systemUiVisibility =
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
    }
}
