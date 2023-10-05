package dev.remaker.sketchubx.core.ui.model

data class Section(
    val title: String,
    val summary: String? = null,
    val type: SectionType = SectionType.GRID,
    val scope: String,
    var projects: ArrayList<Project> = ArrayList()
)

sealed class SectionType(val ordinal: Int) {
    object CAROUSEL : SectionType(0)
    object GRID : SectionType(1)
    object LIST : SectionType(2)
}
