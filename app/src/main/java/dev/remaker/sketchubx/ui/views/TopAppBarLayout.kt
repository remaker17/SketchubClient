package dev.remaker.sketchubx.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.core.view.updateLayoutParams
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
import com.google.android.material.shape.MaterialShapeDrawable
import dev.chrisbanes.insetter.applyInsetter
import dev.remaker.sketchubx.databinding.SimpleAppbarLayoutBinding

class TopAppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : AppBarLayout(context, attrs, defStyleAttr) {
    private var simpleAppbarBinding: SimpleAppbarLayoutBinding? = null

    init {
        simpleAppbarBinding =
            SimpleAppbarLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        simpleAppbarBinding?.root?.applyInsetter {
            type(navigationBars = true) {
                padding(horizontal = true)
            }
        }
        statusBarForeground = MaterialShapeDrawable.createWithElevationOverlay(context)
    }

    fun pinWhenScrolled() {
        simpleAppbarBinding?.root?.updateLayoutParams<LayoutParams> {
            scrollFlags = SCROLL_FLAG_NO_SCROLL
        }
    }

    val toolbar: Toolbar
        get() = simpleAppbarBinding?.toolbar!!

    var title: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }
}
