package dev.remaker.sketchubx.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.transition.TransitionManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.transition.MaterialFadeThrough

class CustomViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        val transition = MaterialFadeThrough()
        val currentView = getChildAt(0)
        val nextView = getChildAt(item)
        transition.addTarget(currentView)
        transition.addTarget(nextView)
        TransitionManager.beginDelayedTransition(this, transition)
        super.setCurrentItem(item, false)
    }

    override fun beginFakeDrag(): Boolean {
        return false
    }

    override fun fakeDragBy(xOffset: Float) {
        // no-op
    }
}
