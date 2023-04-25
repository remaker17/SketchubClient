package dev.remaker.sketchubx.ui.cells

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.models.Project
import dev.remaker.sketchubx.util.AndroidUtilities

class ProjectCarouselCell(context: Context) : FrameLayout(context) {

    private var banner: ImageView
    private var category: TextView
    private var title: TextView
    private var size: TextView
    private var icon: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_project_carousel_cell, this)
        title = findViewById(R.id.title_text)
        icon = findViewById(R.id.icon)
        banner = findViewById(R.id.banner)
        category = findViewById(R.id.category_text)
        size = findViewById(R.id.size_text)
        setPadding(0, AndroidUtilities.dp(8f), 0, AndroidUtilities.dp(8f))
    }

    fun bind(project: Project) {
        title.text = project.title
        category.text = project.category
        size.text = project.fileSize

        Glide.with(icon).load(project.getIconUrl()).into(icon)
        Glide.with(banner)
            .load(project.getScreenshots()[0])
            .into(banner)
    }
}
