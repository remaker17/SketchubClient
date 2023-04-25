package dev.remaker.sketchubx.ui.fragments.search

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.getSystemService
import androidx.core.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import dev.remaker.sketchubx.R
// import dev.remaker.sketchubx.ui.adapters.SearchAdapter
import dev.remaker.sketchubx.databinding.FragmentSearchBinding
import dev.remaker.sketchubx.extensions.*
import dev.remaker.sketchubx.ui.fragments.base.BindingFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.Job
import java.util.*

class SearchFragment :
    BindingFragment<FragmentSearchBinding>(),
    ChipGroup.OnCheckedStateChangeListener {
    companion object {
        const val QUERY = "query"
    }

    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    // private lateinit var searchAdapter: SearchAdapter
    private var query: String? = null

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterTransition = MaterialFadeThrough().addTarget(view)
        reenterTransition = MaterialFadeThrough().addTarget(view)
        mainActivity.setSupportActionBar(binding.toolbar)
        // searchViewModel.clearSearchResult()
        setupRecyclerView()

        binding.clearText.setOnClickListener {
            binding.searchView.clearText()
            // searchAdapter.swapDataSet(listOf())
        }
        binding.searchView.apply {
            doAfterTextChanged {
                if (!it.isNullOrEmpty()) {
                    search(it.toString())
                } else {
                    TransitionManager.beginDelayedTransition(binding.appBarLayout)
                    binding.clearText.isGone = true
                }
            }
            focusAndShowKeyboard()
        }
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY)
        }
        // viewModel.getSearchResult().observe(viewLifecycleOwner) {
        // showData(it)
        // }
        setupChips()
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        binding.appBarLayout.statusBarForeground =
            MaterialShapeDrawable.createWithElevationOverlay(requireContext())
    }

    private fun setupChips() {
        val chips = binding.searchFilterGroup.children.map { it as Chip }
        binding.searchFilterGroup.setOnCheckedStateChangeListener(this)
    }

    private fun showData(data: List<Any>) {
        if (data.isNotEmpty()) {
            // searchAdapter.swapDataSet(data)
        } else {
            // searchAdapter.swapDataSet(ArrayList())
        }
    }

    private fun setupRecyclerView() {
        // searchAdapter = SearchAdapter(requireActivity(), emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            // adapter = searchAdapter
        }
    }

    private fun search(query: String) {
        this.query = query
        TransitionManager.beginDelayedTransition(binding.appBarLayout)
        binding.clearText.isVisible = query.isNotEmpty()
        val filter = getFilter()
        // job?.cancel()
        // job = searchViewModel.search(query, filter)
    }

    private fun getFilter(): Filter {
        return when (binding.searchFilterGroup.checkedChipId) {
            R.id.chip_projects -> Filter.PROJECTS
            R.id.chip_users -> Filter.USERS
            else -> Filter.NO_FILTER
        }
    }

    private val speechInputLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val spokenText: String? =
                    result?.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                binding.searchView.setText(spokenText)
            }
        }

    override fun onDestroyView() {
        hideKeyboard(view)
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(view)
    }

    private fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm =
                requireContext().getSystemService<InputMethodManager>()
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCheckedChanged(group: ChipGroup, checkedIds: MutableList<Int>) {
        search(binding.searchView.text.toString())
    }
}

enum class Filter {
    PROJECTS,
    USERS,
    NO_FILTER
}

fun TextInputEditText.clearText() {
    text = null
}
