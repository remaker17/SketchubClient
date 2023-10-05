package dev.remaker.sketchubx

import android.app.Application
import android.content.res.Configuration
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import dev.remaker.sketchubx.core.prefs.AppSettings
import dev.remaker.sketchubx.core.util.AndroidUtilities
import javax.inject.Inject

@HiltAndroidApp
class AppLoader : Application() {

    @Inject
    lateinit var settings: AppSettings

    override fun onCreate() {
        super.onCreate()
        instance = this
        handler = Handler(mainLooper)

        AppCompatDelegate.setDefaultNightMode(settings.theme)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        try {
            AndroidUtilities.checkDisplaySize(this, newConfig)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private var instance: AppLoader? = null
        private var handler: Handler? = null

        fun getContext(): AppLoader = instance!!

        fun getHandler(): Handler = handler!!
    }
}
