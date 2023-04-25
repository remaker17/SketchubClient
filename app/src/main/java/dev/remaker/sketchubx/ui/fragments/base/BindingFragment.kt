package dev.remaker.sketchubx.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.remaker.sketchubx.ui.activities.MainActivity

abstract class BindingFragment<T : ViewBinding> : Fragment() {
    val mainActivity: MainActivity
        get() = activity as MainActivity

    protected lateinit var binding: T

    abstract fun getViewBinding(): T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::binding.isInitialized) {
            binding = getViewBinding()
        }
        return binding.root
    }
}
