package dev.remaker.sketchubx.ui.adapters

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.databinding.LayoutHomeSectionBinding
import dev.remaker.sketchubx.extensions.resolveColor
import dev.remaker.sketchubx.extensions.show
import dev.remaker.sketchubx.models.Project
import dev.remaker.sketchubx.models.Section
import dev.remaker.sketchubx.models.SectionType
import dev.remaker.sketchubx.ui.cells.ProjectShimmerCell
import dev.remaker.sketchubx.ui.components.RecyclerListView
import dev.remaker.sketchubx.util.AndroidUtilities

class SectionContainer(context: Context, viewType: Int) : FrameLayout(context) {

    private val binding = LayoutHomeSectionBinding.inflate(LayoutInflater.from(context), this, true)
    private val recyclerView: RecyclerListView
    private val shimmerLayout: ShimmerFrameLayout

    private var projectData: ArrayList<Project> = ArrayList()
        set(value) {
            field.clear()
            field.addAll(value)
        }

    companion object {
        private const val DEFAULT_SHIMMER_COUNT = 6
    }

    init {
        recyclerView = RecyclerListView(context).apply {
            setPadding(AndroidUtilities.dp(8f), 0, AndroidUtilities.dp(8f), 0)
            clipToPadding = false
            overScrollMode = View.OVER_SCROLL_NEVER
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.left = AndroidUtilities.getProperPadding() / 2
                    outRect.right = AndroidUtilities.getProperPadding() / 2
                }
            })
        }

        val shimmerColor = context.resolveColor(R.attr.colorSurface)
        val highlightColor = context.resolveColor(R.attr.colorOnSurface)
        val shimmerHighlightColor = ColorUtils.setAlphaComponent(highlightColor, (255f * 0.25f).toInt())

        shimmerLayout = ShimmerFrameLayout(context).apply {
            setShimmer(
                Shimmer.ColorHighlightBuilder()
                    .setBaseColor(shimmerColor)
                    .setHighlightColor(shimmerHighlightColor)
                    .build()
            )

            val linearLayout = LinearLayout(context).apply {
                recyclerView.emptyView = this
            }

            for (i in 0 until DEFAULT_SHIMMER_COUNT) {
                linearLayout.addView(ProjectShimmerCell(context))
            }

            addView(
                linearLayout,
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            startShimmer()
        }

        binding.content.addView(
            recyclerView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        )
        binding.content.addView(shimmerLayout)

        setAdapter(viewType)
    }

    fun bind(section: Section) {
        binding.title.text = section.title
        setSummary(section.summary)
        projectData = section.projects
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setAdapter(viewType: Int) {
        val adapter = when (viewType) {
            SectionType.CAROUSEL.ordinal -> ProjectCarouselAdapter(items = projectData)
            else -> ProjectsAdapter(data = projectData, hideDownloads = true)
        }

        recyclerView.adapter = adapter
    }

    private fun setSummary(text: String?) {
        if (!text.isNullOrEmpty()) {
            binding.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
            binding.summary.text = text
            binding.summary.show()
        }
    }
}
