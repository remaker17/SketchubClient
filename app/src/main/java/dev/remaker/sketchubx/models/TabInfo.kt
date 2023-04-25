package dev.remaker.sketchubx.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.remaker.sketchubx.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class TabInfo(
    val category: Tab,
    var visible: Boolean
) : Parcelable {

    enum class Tab(
        val id: Int,
        @StringRes val stringRes: Int,
        @DrawableRes val icon: Int
    ) {
        Home(R.id.navigation_home, R.string.home, R.drawable.ic_home_24),
        Projects(R.id.navigation_projects, R.string.projects, R.drawable.ic_list_24),
        Announcements(R.id.navigation_announcements, R.string.announcements, R.drawable.ic_campaign_24)
    }
}
