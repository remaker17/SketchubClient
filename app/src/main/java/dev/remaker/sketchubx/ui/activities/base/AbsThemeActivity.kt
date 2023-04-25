package dev.remaker.sketchubx.ui.activities.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.remaker.sketchubx.extensions.setDrawBehindSystemBars
import dev.remaker.sketchubx.util.VersionUtils

abstract class AbsThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDrawBehindSystemBars()
        if (VersionUtils.hasQ()) {
            window.decorView.isForceDarkAllowed = false
        }
    }
}
