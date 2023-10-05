package dev.remaker.sketchubx.core.prefs

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.collection.ArraySet
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import dev.remaker.sketchubx.core.util.ext.connectivityManager
import dev.remaker.sketchubx.core.util.ext.find
import dev.remaker.sketchubx.core.util.ext.observe
import dev.remaker.sketchubx.core.util.ext.mapNotNullToSet
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettings @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val connectivityManager = context.connectivityManager

    val theme: Int
        get() = prefs.getString(KEY_THEME, null)?.toIntOrNull()
            ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

    val isAmoledTheme: Boolean
        get() = prefs.getBoolean(KEY_THEME_AMOLED, false)

    var mainNavItems: List<NavItem>
        get() {
            val raw = prefs.getString(KEY_NAV_MAIN, null)?.split(',')
            return if (raw.isNullOrEmpty()) {
                listOf(NavItem.EXPLORE, NavItem.PROJECTS, NavItem.ANNOUNCEMENTS)
            } else {
                raw.mapNotNull { x -> NavItem.entries.find(x) }.ifEmpty { listOf(NavItem.EXPLORE) }
            }
        }
        set(value) {
            prefs.edit {
                putString(KEY_NAV_MAIN, value.joinToString(",") { it.name })
            }
        }

    fun subscribe(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unsubscribe(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun observe() = prefs.observe()

    fun getAllValues(): Map<String, *> = prefs.all

    fun upsertAll(m: Map<String, *>) {
        prefs.edit {
            m.forEach { e ->
                when (val v = e.value) {
                    is Boolean -> putBoolean(e.key, v)
                    is Int -> putInt(e.key, v)
                    is Long -> putLong(e.key, v)
                    is Float -> putFloat(e.key, v)
                    is String -> putString(e.key, v)
                    is JSONArray -> putStringSet(e.key, v.toStringSet())
                }
            }
        }
    }

    private fun isBackgroundNetworkRestricted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.restrictBackgroundStatus == ConnectivityManager.RESTRICT_BACKGROUND_STATUS_ENABLED
        } else {
            false
        }
    }

    private fun JSONArray.toStringSet(): Set<String> {
        val len = length()
        val result = ArraySet<String>(len)
        for (i in 0 until len) {
            result.add(getString(i))
        }
        return result
    }

    companion object {
        const val KEY_THEME = "theme"
        const val KEY_THEME_AMOLED = "amoled_theme"
        const val KEY_NAV_MAIN = "nav_main"

        // About
        const val KEY_APP_UPDATE = "app_update"
    }
}
