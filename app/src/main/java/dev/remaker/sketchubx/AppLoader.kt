package dev.remaker.sketchubx

import android.app.Application
import android.content.res.Configuration
import android.os.Handler
import com.google.android.material.color.DynamicColors
import dev.remaker.sketchubx.util.AndroidUtilities

class AppLoader : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        handler = Handler(mainLooper)

        DynamicColors.applyToActivitiesIfAvailable(this)
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
