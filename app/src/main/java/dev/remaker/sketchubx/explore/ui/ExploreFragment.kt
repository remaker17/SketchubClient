package dev.remaker.sketchubx.explore.ui

import android.os.Bundle
import android.view.View
import androidx.core.graphics.Insets
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.core.ui.BaseRVFragment
import dev.remaker.sketchubx.core.ui.model.ProjectResponse
import dev.remaker.sketchubx.core.ui.model.Section
import dev.remaker.sketchubx.core.ui.model.SectionType
import dev.remaker.sketchubx.core.util.logD
import dev.remaker.sketchubx.core.util.ext.showToast
import dev.remaker.sketchubx.databinding.FragmentRecyclerBinding
import dev.remaker.sketchubx.explore.ui.adapter.ExploreAdapter
import dev.remaker.sketchubx.net.SketchubApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExploreFragment : BaseRVFragment<ExploreAdapter>() {

    private val sectionList = arrayListOf<Section>(
        Section(type = SectionType.CAROUSEL, title = "Editor's Choice", scope = "editor_choice"),
        Section(title = "Just updated", summary = "Fresh features & content", scope = "recent"),
        Section(title = "Now trending", scope = "trending"),
        Section(title = "Most liked", scope = "most_liked")
    )

    override val adapter = ExploreAdapter(sectionList)

    override fun onViewBindingCreated(binding: FragmentRecyclerBinding, savedInstanceState: Bundle?) {
        super.onViewBindingCreated(binding, savedInstanceState)
        refreshProjects()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // adapter = null
    }

    override fun onSetupRecyclerView(recyclerView: RecyclerView) {
        super.onSetupRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onWindowInsetsChanged(insets: Insets) {

    }

    private fun refreshProjects() {
        val scopeList = sectionList.map { it.scope }
        val apiService = SketchubApiService.create()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                scopeList.zip(sectionList).forEach { (scope, section) ->
                    val projectResponse = apiService.getProjectList(API_KEY, scope)
                    when (projectResponse.status) {
                        ProjectResponse.SUCCESS -> {
                            projectResponse.projects?.let { projects ->
                                section.projects = projects
                                adapter.notifyItemChanged(sectionList.indexOf(section))
                            }
                        }
                        else -> throw Exception("${projectResponse.status}: ${projectResponse.statusCode}")
                    }
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    showToast("Error refreshing projects: ${e.message}")
                    logD(e.message)
                }
            }
        }
    }
}
