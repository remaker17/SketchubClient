package dev.remaker.sketchubx.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainAdapter(fm: FragmentManager, private val fragments: List<Fragment> = emptyList()) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    fun addFragment(fragment: Fragment) {
        fragments.toMutableList().apply {
            add(fragment)
            notifyDataSetChanged()
        }
    }

    fun removeFragment(fragment: Fragment) {
        fragments.toMutableList().apply {
            remove(fragment)
            notifyDataSetChanged()
        }
    }
}
