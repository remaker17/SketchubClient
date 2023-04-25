package dev.remaker.sketchubx.ui.adapters

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar

class ProgressLayout(context: Context) : LinearLayout(context) {

    init {
        val progressBar = ProgressBar(context)
        addView(
            progressBar,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER.toFloat()
            )
        )
        gravity = Gravity.CENTER
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
}
