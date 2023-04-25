package dev.remaker.sketchubx.ui.activities.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.os.ConfigurationCompat
import dev.remaker.sketchubx.LanguageContextWrapper
import dev.remaker.sketchubx.extensions.*
import dev.remaker.sketchubx.util.PreferenceUtil
import dev.remaker.sketchubx.util.VersionUtils
import dev.remaker.sketchubx.util.theme.getNightMode
import java.util.*

abstract class AbsThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        updateTheme()
        super.onCreate(savedInstanceState)
        setEdgeToEdge()
        if (VersionUtils.hasQ()) {
            window.decorView.isForceDarkAllowed = false
        }
    }

    private fun updateTheme() {
        // setTheme(getThemeResValue())
        setDefaultNightMode(getNightMode())
    }

    override fun attachBaseContext(newBase: Context?) {
        val code = PreferenceUtil.languageCode
        val locale = when (code) {
            "auto" -> ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
            else -> Locale.forLanguageTag(code)
        }
        super.attachBaseContext(LanguageContextWrapper.wrap(newBase, locale))
    }
}
