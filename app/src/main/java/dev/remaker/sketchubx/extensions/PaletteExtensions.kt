package dev.remaker.sketchubx.extensions

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

fun hasEnoughContrast(i: Int, i2: Int): Boolean {
    return ColorUtils.calculateContrast(i2, i) >= 2.toDouble()
}

fun hasEnoughLuminance(i: Int): Boolean {
    return ColorUtils.calculateLuminance(i) >= 0.4f.toDouble()
}

fun isBlack(fArr: FloatArray): Boolean {
    return fArr[2] <= 0.035f
}

fun isNearRedLine(fArr: FloatArray): Boolean {
    val f = fArr[0]
    return f in 10.0f..37.0f && fArr[1] <= 0.82f
}

fun isDark(@ColorInt i: Int): Boolean {
    return ColorUtils.calculateLuminance(i) < 0.5
}
