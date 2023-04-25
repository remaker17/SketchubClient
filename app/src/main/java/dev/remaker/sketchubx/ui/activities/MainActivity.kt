package dev.remaker.sketchubx.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.databinding.ActivityMainBinding
import dev.remaker.sketchubx.interfaces.IScrollHelper
import dev.remaker.sketchubx.ui.activities.base.AbsBaseActivity
import dev.remaker.sketchubx.ui.adapters.MainAdapter
import dev.remaker.sketchubx.ui.fragments.announcements.AnnouncementsFragment
import dev.remaker.sketchubx.ui.fragments.home.HomeFragment
import dev.remaker.sketchubx.ui.fragments.projects.ProjectsFragment

class MainActivity : AbsBaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment: Fragment by lazy { HomeFragment() }
    private val projectsFragment: Fragment by lazy { ProjectsFragment() }
    private val announcementsFragment: Fragment by lazy { AnnouncementsFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        binding.navigationView.setOnItemSelectedListener { item ->
            val fragmentPos = when (item.itemId) {
                R.id.navigation_home -> 0
                R.id.navigation_projects -> 1
                R.id.navigation_announcements -> 2
                else -> null
            }
            fragmentPos?.let {
                binding.viewPager.setCurrentItem(it)
            }
            true
        }

        binding.navigationView.setOnItemReselectedListener {
            val fragment = (binding.viewPager.adapter as? MainAdapter)?.getItem(binding.viewPager.currentItem)
            if (fragment is IScrollHelper) {
                fragment.scrollToTop()
            }
        }
    }

    private fun setupViewPager() {
        val fragments = listOf(homeFragment, projectsFragment, announcementsFragment)
        val mainAdapter = MainAdapter(supportFragmentManager, fragments)
        val pageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                val menuItemId = when (position) {
                    0 -> R.id.navigation_home
                    1 -> R.id.navigation_projects
                    2 -> R.id.navigation_announcements
                    else -> null
                }
                menuItemId?.let { binding.navigationView.menu.findItem(it).isChecked = true }
            }
        }

        binding.viewPager.apply {
            adapter = mainAdapter
            addOnPageChangeListener(pageChangeListener)
        }
    }
}
