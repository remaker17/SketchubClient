package dev.remaker.sketchubx.extensions

import androidx.core.view.WindowInsetsCompat
import dev.remaker.sketchubx.core.util.SketchubUtil

fun WindowInsetsCompat?.getBottomInsets(): Int {
    return this?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom ?: SketchubUtil.navigationBarHeight
}
