package dev.remaker.sketchubx.core.util.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

var RecyclerView.firstVisibleItemPosition: Int
    get() = (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
        ?: RecyclerView.NO_POSITION
    set(value) {
        if (value != RecyclerView.NO_POSITION) {
            (layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(value, 0)
        }
    }
