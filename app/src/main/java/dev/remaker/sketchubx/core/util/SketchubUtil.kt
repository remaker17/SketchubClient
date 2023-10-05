package dev.remaker.sketchubx.core.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import dev.remaker.sketchubx.AppLoader.Companion.getContext
import java.util.*

object SketchubUtil {
    fun getScreenSize(context: Context): Point {
        val x: Int = context.resources.displayMetrics.widthPixels
        val y: Int = context.resources.displayMetrics.heightPixels
        return Point(x, y)
    }

    val density: Float
        get() {
            val result = getContext().resources.displayMetrics.density
            return result
        }

    val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = getContext()
                .resources
                .getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = getContext().resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

    val navigationBarHeight: Int
        get() {
            var result = 0
            val resourceId = getContext()
                .resources
                .getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = getContext().resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

    val isLandscape: Boolean
        get() = (
            getContext().resources.configuration.orientation
                == Configuration.ORIENTATION_LANDSCAPE
            )
    val isTablet: Boolean
        get() = (
            getContext().resources.configuration.smallestScreenWidthDp
                >= 600
            )
}
