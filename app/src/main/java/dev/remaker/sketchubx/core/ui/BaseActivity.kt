package dev.remaker.sketchubx.core.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.EntryPointAccessors
import dev.remaker.sketchubx.BuildConfig
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.core.ui.util.BaseActivityEntryPoint
import dev.remaker.sketchubx.core.ui.util.WindowInsetsDelegate

@Suppress("LeakingThis")
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(),
    WindowInsetsDelegate.WindowInsetsListener {

    private var isAmoledTheme = false

    lateinit var viewBinding: B
        private set

    @JvmField
    protected val insetsDelegate = WindowInsetsDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        val settings = EntryPointAccessors.fromApplication(this, BaseActivityEntryPoint::class.java).settings
        isAmoledTheme = settings.isAmoledTheme
        if (isAmoledTheme) {
            setTheme(R.style.ThemeOverlay_Sketchub_Amoled)
        }
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        insetsDelegate.handleImeInsets = true
        insetsDelegate.addInsetsListener(this)
        putDataToExtras(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        putDataToExtras(intent)
        super.onNewIntent(intent)
    }

    @Deprecated("Use ViewBinding", level = DeprecationLevel.ERROR)
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupToolbar()
    }

    @Deprecated("Use ViewBinding", level = DeprecationLevel.ERROR)
    override fun setContentView(view: View?) {
        super.setContentView(view)
        setupToolbar()
    }

    protected fun setContentView(binding: B) {
        this.viewBinding = binding
        super.setContentView(binding.root)
        val toolbar = (binding.root.findViewById<View>(R.id.toolbar) as? Toolbar)
        toolbar?.let(this::setSupportActionBar)
        insetsDelegate.onViewCreated(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem) = if (item.itemId == android.R.id.home) {
        onBackPressed()
        true
    } else super.onOptionsItemSelected(item)

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (BuildConfig.DEBUG && keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            ActivityCompat.recreate(this)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setupToolbar() {
        (findViewById<View>(R.id.toolbar) as? Toolbar)?.let(this::setSupportActionBar)
    }

    protected fun isDarkAmoledTheme(): Boolean {
        val uiMode = resources.configuration.uiMode
        val isNight = uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        return isNight && isAmoledTheme
    }

    private fun putDataToExtras(intent: Intent?) {
        intent?.putExtra(EXTRA_DATA, intent.data)
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}
