package dev.remaker.sketchubx.projects.ui

import android.os.Bundle
import android.view.View
import androidx.core.graphics.Insets
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.core.ui.BaseRVFragment
import dev.remaker.sketchubx.core.ui.model.Project
import dev.remaker.sketchubx.core.ui.model.ProjectResponse
import dev.remaker.sketchubx.core.util.logD
import dev.remaker.sketchubx.core.util.ext.showToast
import dev.remaker.sketchubx.databinding.FragmentRecyclerBinding
import dev.remaker.sketchubx.net.SketchubApiService
import dev.remaker.sketchubx.projects.ui.adapter.ProjectsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsFragment : BaseRVFragment<ProjectsAdapter>() {

    private val projectList = arrayListOf<Project>()

    private var currentPage = 1
    private var totalPages = 20
    private var isLoading = false

    override val adapter = ProjectsAdapter(data = projectList, listType = 1)

    override fun onViewBindingCreated(binding: FragmentRecyclerBinding, savedInstanceState: Bundle?) {
        super.onViewBindingCreated(binding, savedInstanceState)
        refreshProjects(currentPage)
    }

    override fun onSetupRecyclerView(recyclerView: RecyclerView) {
        super.onSetupRecyclerView(recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val itemCount = layoutManager.itemCount

                    val isLastItemVisible = lastVisibleItemPosition + 1 == itemCount
                    val isLoaderVisible = recyclerView.findViewHolderForAdapterPosition(lastVisibleItemPosition)
                        ?.itemViewType == 1

                    if (!isLoading && isLastItemVisible && isLoaderVisible) {
                        if (currentPage < totalPages) {
                            refreshProjects(currentPage + 1)
                        }
                    }
                }
            })
        }
    }

    override fun onWindowInsetsChanged(insets: Insets) {

    }

    private fun refreshProjects(page: Int) {
        isLoading = true
        val apiService = SketchubApiService.create()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getProjectList(API_KEY)
                when (response.status) {
                    ProjectResponse.SUCCESS -> {
                        totalPages = response.totalPagesAsInt()
                        val newProjectList = response.projects
                        if (currentPage == 1) {
                            projectList.clear()
                            projectList.addAll(newProjectList as Collection<Project>)
                            adapter.notifyDataSetChanged()
                        } else {
                            projectList.addAll(newProjectList as Collection<Project>)
                            adapter.notifyItemRangeInserted(
                                projectList.size - newProjectList?.size!!,
                                newProjectList?.size!!
                            )
                        }
                        currentPage++
                        isLoading = false
                        if (currentPage > totalPages) {
                            adapter.setLoadingVisible(false)
                        } else {
                            adapter.setLoadingVisible(true)
                        }
                    }
                    else -> throw Exception("Error: ${response.message}")
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    showToast(e.message ?: "Unknown error occurred.")
                    logD(e.message)
                }
            }
        }
    }
}
