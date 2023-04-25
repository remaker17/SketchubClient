package dev.remaker.sketchubx.ui.fragments.announcements

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.extensions.showToast
import dev.remaker.sketchubx.models.ProjectResponse
import dev.remaker.sketchubx.net.SketchubApiService
import dev.remaker.sketchubx.ui.adapters.AnnouncementsAdapter
import dev.remaker.sketchubx.ui.fragments.base.RecyclerViewFragment
import dev.remaker.sketchubx.util.logD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnnouncementsFragment : RecyclerViewFragment<AnnouncementsAdapter>() {

    override val adapter = AnnouncementsAdapter()
    private val list = arrayListOf<HashMap<String, String>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        refreshAnnouncements()
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
