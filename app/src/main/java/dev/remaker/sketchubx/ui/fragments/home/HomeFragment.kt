package dev.remaker.sketchubx.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.extensions.showToast
import dev.remaker.sketchubx.models.ProjectResponse
import dev.remaker.sketchubx.models.Section
import dev.remaker.sketchubx.models.SectionType
import dev.remaker.sketchubx.net.SketchubApiService
import dev.remaker.sketchubx.ui.adapters.SectionsAdapter
import dev.remaker.sketchubx.ui.fragments.base.RecyclerViewFragment
import dev.remaker.sketchubx.util.logD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : RecyclerViewFragment<SectionsAdapter>() {

    private val sectionList = arrayListOf<Section>(
        Section(type = SectionType.CAROUSEL, title = "Editor's Choice", scope = "editor_choice"),
        Section(title = "Just updated", summary = "Fresh features & content", scope = "recent"),
        Section(title = "Now trending", scope = "trending"),
        Section(title = "Most liked", scope = "most_liked")
    )

    override val adapter = SectionsAdapter(sectionList)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        refreshProjects()
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
