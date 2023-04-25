package dev.remaker.sketchubx.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.models.Project
import dev.remaker.sketchubx.ui.cells.ProjectCell
import dev.remaker.sketchubx.ui.cells.ProjectListCell
import dev.remaker.sketchubx.ui.components.RecyclerListView

class ProjectsAdapter(
    val data: ArrayList<Project>,
    var loadVisible: Boolean = false,
    var hideDownloads: Boolean = false,
    val listType: Int = 0
) : RecyclerListView.SelectionAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = when {
            viewType == 1 -> ProgressLayout(parent.context)
            listType == 0 -> ProjectCell(parent.context)
            else -> ProjectListCell(parent.context).apply {
                layoutParams = FrameLayout.LayoutParams(-1, -2)
            }
        }
        return RecyclerListView.Holder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1) {
            return
        }

        if (listType == 0) {
            val cell = holder.itemView as ProjectCell
            val project = data[position]

            cell.bind(project, hideDownloads)
            return
        }

        val cell = holder.itemView as ProjectListCell
        val project = data[position]

        cell.bind(project)
    }

    override fun getItemCount(): Int {
        if (data.isNullOrEmpty()) {
            return 0
        }
        return if (loadVisible) data.size + 1 else data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != data.size || !loadVisible) 0 else 1
    }

    override fun isEnabled(holder: RecyclerView.ViewHolder): Boolean {
        return false
    }

    fun setLoadingVisible(value: Boolean) {
        if (loadVisible == value) {
            return
        }

        loadVisible = value

        if (value) {
            notifyItemInserted(itemCount + 1)
        } else {
            notifyItemRemoved(itemCount + 1)
        }
    }
}
