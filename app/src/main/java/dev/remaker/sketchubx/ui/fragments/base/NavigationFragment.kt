package dev.remaker.sketchubx.ui.fragments.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.extensions.applyToolbar

abstract class NavigationFragment<T : ViewBinding> : BindingFragment<T>() {

    abstract fun getAppBar(): AppBarLayout
    abstract fun getToolbar(): Toolbar

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.applyToolbar(getToolbar())
    }

    companion object {
        const val API_KEY: String = BuildConfig.API_KEY
    }
}
