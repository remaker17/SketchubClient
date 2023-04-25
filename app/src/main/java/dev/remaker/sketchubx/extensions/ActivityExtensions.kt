package dev.remaker.sketchubx.extensions

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.getSystemService

fun AppCompatActivity.applyToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
}

fun AppCompatActivity.hideSoftKeyboard() {
    val currentFocus: View? = currentFocus
    if (currentFocus != null) {
        val inputMethodManager =
            getSystemService<InputMethodManager>()
        inputMethodManager?.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

inline val Activity.rootView: View get() = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
