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

    private val announcementsFragment = AnnouncementsFragment()
    private val exploreFragment = ExploreFragment()
    private val projectsFragment = ProjectsFragment()
    private var activeFragment: Fragment = exploreFragment

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
        navBar.selectedItemId = firstVisibleItemId() ?: return
    }

    fun onCreate(lifecycleOwner: LifecycleOwner, savedInstanceState: Bundle?) {
        if (navBar.menu.isEmpty()) {
            createMenuItems(settings.mainNavItems, navBar.menu)
        }
        createDefaultFragments(savedInstanceState)
        val fragment = primaryFragment
        if (fragment != null) {
            onFragmentChanged(fragment, fromUser = false)
            val itemId = getItemId(fragment)
            if (navBar.selectedItemId != itemId) {
                navBar.selectedItemId = itemId
            }
        } else {
            val itemId = if (savedInstanceState == null) {
                firstVisibleItemId() ?: navBar.selectedItemId
            } else {
                navBar.selectedItemId
            }
            onNavigationItemSelected(itemId)
        }
    }

    private fun createDefaultFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .add(R.id.container, announcementsFragment, TAG_PRIMARY).hide(announcementsFragment)
                .add(R.id.container, projectsFragment, TAG_PRIMARY).hide(projectsFragment)
                .add(R.id.container, exploreFragment, TAG_PRIMARY)
                .commit()
        }
    }

    fun setCounter(item: NavItem, counter: Int) {
        setCounter(item.id, counter)
    }

    private fun setCounter(@IdRes id: Int, counter: Int) {
        val badge = navBar.getBadge(id)
        if (counter == 0) {
            badge?.isVisible = false
        } else {
            badge?.apply {
                if (counter < 0) {
                    clearNumber()
                } else {
                    number = counter
                }
                isVisible = true
            } ?: run {
                val newBadge = navBar.getOrCreateBadge(id)
                if (counter < 0) {
                    newBadge.clearNumber()
                } else {
                    newBadge.number = counter
                }
                newBadge.isVisible = true
            }
        }
    }

    fun setItemVisibility(@IdRes itemId: Int, isVisible: Boolean) {
        val item = navBar.menu.findItem(itemId)
        item?.isVisible = isVisible
        if (item?.isChecked == true && !isVisible) {
            navBar.selectedItemId = firstVisibleItemId() ?: return
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
        else -> throw IllegalStateException()
    }

    private fun setPrimaryFragment(fragment: Fragment): Boolean {
        if (fragmentManager.isStateSaved) {
            return false
        }
        fragment.enterTransition = MaterialFadeThrough()
        fragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .show(fragment).hide(activeFragment)
            .commit()
        onFragmentChanged(fragment, fromUser = true)
        return true
    }

    private fun onFragmentChanged(fragment: Fragment, fromUser: Boolean) {
        activeFragment = fragment
        isEnabled = getItemId(fragment) != firstVisibleItemId()
        listeners.forEach { it.onFragmentChanged(fragment, fromUser) }
    }

    private fun createMenuItems(items: List<NavItem>, menu: Menu) {
        items.forEach { item ->
            menu.add(Menu.NONE, item.id, Menu.NONE, item.title)
                .setIcon(item.icon)
        }
    }

    private fun firstVisibleItemId(): Int? {
        val menu = navBar.menu
        for (item in menu) {
            if (item.isVisible) return item.itemId
        }
        return null
    }

    interface OnFragmentChangedListener {
        fun onFragmentChanged(fragment: Fragment, fromUser: Boolean)
    }
}
