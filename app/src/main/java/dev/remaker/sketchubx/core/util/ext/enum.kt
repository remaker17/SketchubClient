package dev.remaker.sketchubx.core.util.ext

import kotlin.enums.EnumEntries

fun <E : Enum<E>> EnumEntries<E>.find(name: String): E? {
    return find { x -> x.name == name }
}
