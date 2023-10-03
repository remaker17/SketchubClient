package dev.remaker.sketchubx.ui.fragments.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import androidx.core.view.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.MaterialFadeThrough
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.databinding.FragmentRecyclerBinding
import dev.remaker.sketchubx.interfaces.IScrollHelper

abstract class AbsRecyclerViewFragment<V : ViewBinding, T : RecyclerView.Adapter<*>> : NavigationFragment<V>() {
    abstract fun getRecyclerView(): RecyclerView
    abstract val adapter: T

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterTransition = MaterialFadeThrough().addTarget(R.id.refresh_layout)
        exitTransition = MaterialFadeThrough().addTarget(R.id.refresh_layout)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        setupRecyclerView(getRecyclerView())
    }

    protected open fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }
}

abstract class RecyclerViewFragment<T : RecyclerView.Adapter<*>> :
    AbsRecyclerViewFragment<FragmentRecyclerBinding, T>(), IScrollHelper {

    override fun getViewBinding() = FragmentRecyclerBinding.inflate(layoutInflater)
    override fun getToolbar(): Toolbar = binding.toolbar
    override fun getAppBar() = binding.appBarLayout
    override fun getRecyclerView() = binding.recyclerView
    abstract override val adapter: T

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun scrollToTop() {
        getRecyclerView().smoothScrollToPosition(0)
        getAppBar().setExpanded(true, true)
    }
}
