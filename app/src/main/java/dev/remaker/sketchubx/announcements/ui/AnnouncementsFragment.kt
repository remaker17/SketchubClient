package dev.remaker.sketchubx.announcements.ui

import android.os.Bundle
import androidx.core.graphics.Insets
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.announcements.ui.adapter.AnnouncementsAdapter
import dev.remaker.sketchubx.core.ui.BaseRVFragment
import dev.remaker.sketchubx.core.ui.model.ProjectResponse
import dev.remaker.sketchubx.core.util.ext.showToast
import dev.remaker.sketchubx.core.util.logD
import dev.remaker.sketchubx.databinding.FragmentRecyclerBinding
import dev.remaker.sketchubx.net.SketchubApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnnouncementsFragment : BaseRVFragment<AnnouncementsAdapter>() {

    override val adapter = AnnouncementsAdapter()
    private val list = arrayListOf<HashMap<String, String>>()

    override fun onViewBindingCreated(binding: FragmentRecyclerBinding, savedInstanceState: Bundle?) {
        super.onViewBindingCreated(binding, savedInstanceState)
        refreshAnnouncements()
    }

    override fun onSetupRecyclerView(recyclerView: RecyclerView) {
        super.onSetupRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onWindowInsetsChanged(insets: Insets) {
    }

    private fun refreshAnnouncements() {
        val apiService = SketchubApiService.create()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val announcementResponse = apiService.getAnnouncementList(API_KEY)
                when (announcementResponse.status) {
                    ProjectResponse.SUCCESS -> {
                        list.clear()
                        list.addAll(announcementResponse.news)
                        adapter.items = list
                    }
                    else -> throw Exception("${announcementResponse.status}: ${announcementResponse.statusCode}")
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    showToast("Error refreshing announcements: ${e.message}")
                    logD(e.message)
                }
            }
        }
    }
}
