package dev.remaker.sketchubx.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class NoAnimViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, false)
    }

    override fun beginFakeDrag(): Boolean {
        return false
    }

    override fun fakeDragBy(xOffset: Float) {
        // no-op
    }
}
