package dev.remaker.sketchubx.core.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import dev.chrisbanes.insetter.applyInsetter

class InsetsRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    init {
        if (!isInEditMode) {
            applyInsetter {
                type(navigationBars = true) {
                    padding(vertical = true)
                }
            }
        }
    }
}
