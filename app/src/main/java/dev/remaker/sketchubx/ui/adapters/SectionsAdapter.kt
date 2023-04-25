package dev.remaker.sketchubx.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.models.Section
import dev.remaker.sketchubx.ui.components.RecyclerListView

class SectionsAdapter(private val items: ArrayList<Section>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = SectionContainer(context = parent.context, viewType = viewType)
        return RecyclerListView.Holder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val container = holder.itemView as SectionContainer
        val section = items[position]

        container.bind(section)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val sectionType = items[position].type.ordinal
        return sectionType
    }
}
