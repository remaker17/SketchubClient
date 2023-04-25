package dev.remaker.sketchubx.ui.adapters

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.ui.cells.AnnouncementCell
import dev.remaker.sketchubx.ui.components.RecyclerListView

class AnnouncementsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = arrayListOf<HashMap<String, String>>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = AnnouncementCell(parent.context)
        view.layoutParams = FrameLayout.LayoutParams(-1, -2)
        return RecyclerListView.Holder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cell = holder.itemView as AnnouncementCell
        val hashMap = items[position]
        cell.bind(hashMap)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
