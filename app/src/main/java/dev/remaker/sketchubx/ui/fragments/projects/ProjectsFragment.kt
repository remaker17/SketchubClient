package dev.remaker.sketchubx.ui.fragments.projects

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.extensions.showToast
import dev.remaker.sketchubx.models.Project
import dev.remaker.sketchubx.models.ProjectResponse
import dev.remaker.sketchubx.net.SketchubApiService
import dev.remaker.sketchubx.ui.adapters.ProjectsAdapter
import dev.remaker.sketchubx.ui.fragments.base.RecyclerViewFragment
import dev.remaker.sketchubx.util.logD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsFragment : RecyclerViewFragment<ProjectsAdapter>() {

    private val projectList = arrayListOf<Project>()

    private var currentPage = 1
    private var totalPages = 20
    private var isLoading = false

    override val adapter = ProjectsAdapter(data = projectList, listType = 1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
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
        refreshProjects(currentPage)
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
