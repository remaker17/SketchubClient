package dev.remaker.sketchubx.core.util.ext

import androidx.collection.ArraySet
import java.util.*

inline fun <T, R> Collection<T>.mapNotNullToSet(transform: (T) -> R?): Set<R> {
    val destination = ArraySet<R>(size)
    for (item in this) {
        destination.add(transform(item) ?: continue)
    }
    return destination
}
