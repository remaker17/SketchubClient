package dev.remaker.sketchubx.main.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.core.prefs.AppSettings
import dev.remaker.sketchubx.core.ui.BaseActivity
import dev.remaker.sketchubx.databinding.ActivityMainBinding
import dev.remaker.sketchubx.main.ui.owners.AppBarOwner
import dev.remaker.sketchubx.main.ui.owners.BottomNavOwner
import javax.inject.Inject
import com.google.android.material.R as materialR

private const val TAG_SEARCH = "search"

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding>(),
    AppBarOwner,
    BottomNavOwner,
    MainNavigationDelegate.OnFragmentChangedListener {

    @Inject
    lateinit var settings: AppSettings

    private lateinit var navigationDelegate: MainNavigationDelegate

    override val appBar: AppBarLayout
        get() = viewBinding.appbar

    override val bottomNav: BottomNavigationView?
        get() = viewBinding.bottomNav

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater))

        navigationDelegate = MainNavigationDelegate(
            navBar = checkNotNull(bottomNav),
            fragmentManager = supportFragmentManager,
            settings = settings
        )
        navigationDelegate.addOnFragmentChangedListener(this)
        navigationDelegate.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(navigationDelegate)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        navigationDelegate.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navigationDelegate.onRestoreInstanceState(savedInstanceState)
        adjustSearchUI(isSearchOpened(), animate = false)
    }

    override fun onFragmentChanged(fragment: Fragment, fromUser: Boolean) {
        if (fromUser) {
            //viewBinding.appbar.setExpanded(true)
        }
    }

    override fun onWindowInsetsChanged(insets: Insets) {
    }

    private fun isSearchOpened(): Boolean {
        return supportFragmentManager.findFragmentByTag(TAG_SEARCH) != null
    }

    private fun adjustSearchUI(isOpened: Boolean, animate: Boolean) {
        if (animate) {
            TransitionManager.beginDelayedTransition(viewBinding.appbar)
        }
        val appBarScrollFlags = if (isOpened) {
            SCROLL_FLAG_NO_SCROLL
        } else {
            SCROLL_FLAG_SCROLL or SCROLL_FLAG_ENTER_ALWAYS or SCROLL_FLAG_SNAP
        }
        viewBinding.toolbarCard.updateLayoutParams<AppBarLayout.LayoutParams> {
            scrollFlags = appBarScrollFlags
        }
        viewBinding.insetsHolder.updateLayoutParams<AppBarLayout.LayoutParams> {
            scrollFlags = appBarScrollFlags
        }
        viewBinding.toolbarCard.background = if (isOpened) {
            null
        } else {
            ContextCompat.getDrawable(this, R.drawable.search_bar_background)
        }
        val padding = if (isOpened) 0 else resources.getDimensionPixelOffset(R.dimen.margin_normal)
        viewBinding.appbar.updatePadding(left = padding, right = padding)
        supportActionBar?.apply {
            setHomeAsUpIndicator(
                when {
                    isOpened -> materialR.drawable.abc_ic_ab_back_material
                    else -> materialR.drawable.abc_ic_search_api_material
                }
            )
            setHomeActionContentDescription(
                if (isOpened) R.string.back else R.string.search
            )
        }
        viewBinding.searchView.setHintCompat(
            if (isOpened) R.string.search_hint else R.string.search_project
        )
        bottomNav?.visibility = if (isOpened) View.GONE else View.VISIBLE
    }
}
