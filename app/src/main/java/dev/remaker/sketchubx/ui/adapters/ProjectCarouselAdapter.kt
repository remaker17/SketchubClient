package dev.remaker.sketchubx.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.models.Project
import dev.remaker.sketchubx.ui.cells.ProjectCarouselCell
import dev.remaker.sketchubx.ui.components.RecyclerListView

class ProjectCarouselAdapter(var items: ArrayList<Project>) : RecyclerListView.SelectionAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerListView.Holder(ProjectCarouselCell(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val project = items[position]
        val cell = holder.itemView as ProjectCarouselCell

        cell.bind(project)

//        Unstable code, it is not recommended to use it.
//        val handler = Handler()
//        val runnable = object : Runnable {
//            var i = 0
//            override fun run() {
//                Glide.with(cell.banner)
//                    .load(project.screenshots[i])
//                    .into(cell.banner)
//
//                i++
//                if (i >= project.screenshots.size) {
//                    i = 0
//                }
//
//                handler.postDelayed(this, 5000)
//            }
//        }
//
//        handler.postDelayed(runnable, 0)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun isEnabled(holder: RecyclerView.ViewHolder): Boolean {
        return true
    }
}
