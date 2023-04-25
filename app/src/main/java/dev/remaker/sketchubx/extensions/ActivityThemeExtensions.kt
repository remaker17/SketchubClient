package dev.remaker.sketchubx.extensions

import android.graphics.Color
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.*
import dev.remaker.sketchubx.util.VersionUtils

fun AppCompatActivity.setEdgeToEdge() {
    setDrawBehindSystemBars()
}

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
        systemUiVisibility = if (enabled) {
            systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        decorView.systemUiVisibility = systemUiVisibility
    }
}

fun AppCompatActivity.hideSoftKeyboard() {
    val currentFocus: View? = currentFocus
    if (currentFocus != null) {
        val inputMethodManager =
            getSystemService<InputMethodManager>()
        inputMethodManager?.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}
