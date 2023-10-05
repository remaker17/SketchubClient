package dev.remaker.sketchubx.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.core.ui.util.WindowInsetsDelegate

@Suppress("LeakingThis")
abstract class BaseFragment<B : ViewBinding> : Fragment(),
    WindowInsetsDelegate.WindowInsetsListener {

    var viewBinding: B? = null
        private set

    @Deprecated("", ReplaceWith("requireViewBinding()"))
    protected val binding: B
        get() = requireViewBinding()

    @JvmField
    protected val insetsDelegate = WindowInsetsDelegate()

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = onCreateViewBinding(inflater, container)
        viewBinding = binding
        return binding.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insetsDelegate.onViewCreated(view)
        insetsDelegate.addInsetsListener(this)
        onViewBindingCreated(requireViewBinding(), savedInstanceState)
    }

    override fun onDestroyView() {
        viewBinding = null
        insetsDelegate.removeInsetsListener(this)
        insetsDelegate.onDestroyView()
        super.onDestroyView()
    }

    fun requireViewBinding(): B = checkNotNull(viewBinding) {
        "Fragment $this did not return a ViewBinding from onCreateView() or this was called before onCreateView()."
    }

    @Deprecated("", ReplaceWith("viewBinding"))
    protected fun bindingOrNull() = viewBinding

    protected abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    protected open fun onViewBindingCreated(binding: B, savedInstanceState: Bundle?) = Unit

    companion object {
        const val API_KEY: String = BuildConfig.API_KEY
    }
}
