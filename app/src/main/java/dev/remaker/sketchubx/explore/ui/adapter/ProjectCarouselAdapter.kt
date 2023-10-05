package dev.remaker.sketchubx.explore.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.core.ui.cell.ProjectCarouselCell
import dev.remaker.sketchubx.core.ui.component.RecyclerListView
import dev.remaker.sketchubx.core.ui.model.Project

class ProjectCarouselAdapter(var items: ArrayList<Project>) : RecyclerListView.SelectionAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerListView.Holder(ProjectCarouselCell(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val project = items[position]
        val cell = holder.itemView as ProjectCarouselCell

        cell.bind(project)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun isEnabled(holder: RecyclerView.ViewHolder): Boolean {
        return true
    }
}
