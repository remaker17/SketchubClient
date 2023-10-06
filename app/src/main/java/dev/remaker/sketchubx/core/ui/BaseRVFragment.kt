package dev.remaker.sketchubx.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.databinding.FragmentRecyclerBinding

abstract class BaseRVFragment<T : RecyclerView.Adapter<*>> :
    BaseFragment<FragmentRecyclerBinding>() {
    abstract val adapter: T

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRecyclerBinding {
        return FragmentRecyclerBinding.inflate(inflater, container, false)
    }

    override fun onViewBindingCreated(binding: FragmentRecyclerBinding, savedInstanceState: Bundle?) {
        onSetupRecyclerView(binding.recyclerView)
    }

    protected open fun onSetupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }
}
