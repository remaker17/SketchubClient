package dev.remaker.sketchubx.list.ui

import androidx.recyclerview.widget.DiffUtil
import dev.remaker.sketchubx.list.ui.model.ListModel

open class ListModelDiffCallback<T : ListModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return newItem.getChangePayload(oldItem)
    }

    companion object : ListModelDiffCallback<ListModel>() {
        val PAYLOAD_CHECKED_CHANGED = Any()
        val PAYLOAD_NESTED_LIST_CHANGED = Any()
        val PAYLOAD_PROGRESS_CHANGED = Any()
        val PAYLOAD_ANYTHING_CHANGED = Any()
    }
}
