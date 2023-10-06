package dev.remaker.sketchubx.core.prefs

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.list.ui.model.ListModel

enum class NavItem(
    @IdRes val id: Int,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) : ListModel {

    EXPLORE(R.id.nav_explore, R.string.explore, R.drawable.ic_home_24),
    PROJECTS(R.id.nav_projects, R.string.projects, R.drawable.ic_list_24),
    ANNOUNCEMENTS(R.id.nav_announcements, R.string.announcements, R.drawable.ic_campaign_24)
    ;

    override fun areItemsTheSame(other: ListModel): Boolean {
        return other is NavItem && ordinal == other.ordinal
    }
}
