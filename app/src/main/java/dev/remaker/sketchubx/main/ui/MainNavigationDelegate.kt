package dev.remaker.sketchubx.main.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.core.view.isEmpty
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.transition.MaterialFadeThrough
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.announcements.ui.AnnouncementsFragment
import dev.remaker.sketchubx.core.prefs.AppSettings
import dev.remaker.sketchubx.core.prefs.NavItem
import dev.remaker.sketchubx.core.ui.util.RecyclerViewOwner
import dev.remaker.sketchubx.core.util.ext.firstVisibleItemPosition
import dev.remaker.sketchubx.core.util.ext.isAnimationsEnabled
import dev.remaker.sketchubx.explore.ui.ExploreFragment
import dev.remaker.sketchubx.projects.ui.ProjectsFragment
import java.util.LinkedList

private const val TAG_PRIMARY = "primary"

class MainNavigationDelegate(
    private val navBar: NavigationBarView,
    private val fragmentManager: FragmentManager,
    private val settings: AppSettings
) : OnBackPressedCallback(false),
    NavigationBarView.OnItemSelectedListener,
    NavigationBarView.OnItemReselectedListener {

    private val listeners = LinkedList<OnFragmentChangedListener>()

    private var active: Fragment? = null
    private var announcementsFragment: AnnouncementsFragment? = null
    private var exploreFragment: ExploreFragment? = null
    private var projectsFragment: ProjectsFragment? = null

    val primaryFragment: Fragment?
        get() = fragmentManager.findFragmentByTag(TAG_PRIMARY)

    init {
        navBar.setOnItemSelectedListener(this)
        navBar.setOnItemReselectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return onNavigationItemSelected(item.itemId)
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val fragment = fragmentManager.findFragmentByTag(TAG_PRIMARY)
        if (fragment == null || fragment !is RecyclerViewOwner || fragment.view == null) {
            return
        }
        val recyclerView = fragment.recyclerView
        if (recyclerView.context.isAnimationsEnabled) {
            recyclerView.smoothScrollToPosition(0)
        } else {
            recyclerView.firstVisibleItemPosition = 0
        }
    }

    override fun handleOnBackPressed() {
        navBar.selectedItemId = firstItem()?.itemId ?: return
    }

    fun onCreate(lifecycleOwner: LifecycleOwner, savedInstanceState: Bundle?) {
        if (navBar.menu.isEmpty()) {
            createMenu(settings.mainNavItems, navBar.menu)
        }
        createFragments(savedInstanceState)
        val fragment = primaryFragment
        if (fragment != null) {
            onFragmentChanged(fragment, fromUser = false)
            val itemId = getItemId(fragment)
            if (navBar.selectedItemId != itemId) {
                navBar.selectedItemId = itemId
            }
        } else {
            val itemId = if (savedInstanceState == null) {
                firstItem()?.itemId ?: navBar.selectedItemId
            } else {
                navBar.selectedItemId
            }
            onNavigationItemSelected(itemId)
        }
    }

    private fun createFragment(fragment: Fragment?): Fragment {
        if (fragment is ExploreFragment) {
            exploreFragment = ExploreFragment()
            fragment = ExploreFragment()
        } else if (fragment is ProjectsFragment) {
            projectsFragment = ProjectsFragment()
            fragment = projectsFragment
        } else if (fragment is AnnouncementsFragment) {
            announcementsFragment = AnnouncementsFragment()
            fragment = announcementsFragment
        } else {
            throw IllegalStateException()
        }
        return fragment
    }

    private fun createFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null)
            fragmentManager.beginTransaction()
                .add(R.id.container, createFragment(exploreFragment), TAG_PRIMARY)
                .commit()
        }
    }

    fun setCounter(item: NavItem, counter: Int) {
        setCounter(item.id, counter)
    }

    private fun setCounter(@IdRes id: Int, counter: Int) {
        if (counter == 0) {
            navBar.getBadge(id)?.isVisible = false
        } else {
            val badge = navBar.getOrCreateBadge(id)
            if (counter < 0) {
                badge.clearNumber()
            } else {
                badge.number = counter
            }
            badge.isVisible = true
        }
    }

    fun setItemVisibility(@IdRes itemId: Int, isVisible: Boolean) {
        val item = navBar.menu.findItem(itemId) ?: return
        item.isVisible = isVisible
        if (item.isChecked && !isVisible) {
            navBar.selectedItemId = firstItem()?.itemId ?: return
        }
    }

    fun addOnFragmentChangedListener(listener: OnFragmentChangedListener) {
        listeners.add(listener)
    }

    fun removeOnFragmentChangedListener(listener: OnFragmentChangedListener) {
        listeners.remove(listener)
    }

    private fun onNavigationItemSelected(@IdRes itemId: Int): Boolean {
        return setPrimaryFragment(
            when (itemId) {
                R.id.nav_announcements -> announcementsFragment
                R.id.nav_explore -> exploreFragment
                R.id.nav_projects -> projectsFragment
                else -> return false
            }
        )
    }

    private fun getItemId(fragment: Fragment) = when (fragment) {
        is AnnouncementsFragment -> R.id.nav_announcements
        is ExploreFragment -> R.id.nav_explore
        is ProjectsFragment -> R.id.nav_projects
        else -> 0
    }

    private fun setPrimaryFragment(fragment: Fragment?): Boolean {
        if (fragmentManager.isStateSaved) {
            return false
        }
        if (fragment == null) {
            fragment = createFragment(fragment)
        }
        fragment.enterTransition = MaterialFadeThrough()
        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .show(fragment).hide(active)
            //.replace(R.id.container, fragment, TAG_PRIMARY)
            .commit()
        onFragmentChanged(fragment, fromUser = true)
        return true
    }

    private fun onFragmentChanged(fragment: Fragment, fromUser: Boolean) {
        active = fragment
        isEnabled = getItemId(fragment) != firstItem()?.itemId
        listeners.forEach { it.onFragmentChanged(fragment, fromUser) }
    }

    private fun createMenu(items: List<NavItem>, menu: Menu) {
        for (item in items) {
            menu.add(Menu.NONE, item.id, Menu.NONE, item.title)
                .setIcon(item.icon)
        }
    }

    private fun firstItem(): MenuItem? {
        val menu = navBar.menu
        for (item in menu) {
            if (item.isVisible) return item
        }
        return null
    }

    interface OnFragmentChangedListener {
        fun onFragmentChanged(fragment: Fragment, fromUser: Boolean)
    }
}
