package dev.remaker.sketchubx.ui.activities.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.extensions.rootView
import dev.remaker.sketchubx.util.VersionUtils
import dev.remaker.sketchubx.util.logD

abstract class AbsBaseActivity : AbsThemeActivity() {

    private lateinit var permissions: Array<String>
    private lateinit var permissionDeniedMessage: String

    private val snackBarContainer: View
        get() = rootView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionDeniedMessage = getString(R.string.permissions_denied)
        permissions = getPermissionsToRequest()
    }

    override fun onResume() {
        super.onResume()
        if (VersionUtils.hasMarshmallow()) {
            val hasPermissions = hasPermissions()
            if (hasPermissions) {
                onHasPermissionsChanged(true)
            } else {
                requestPermissions()
            }
        }
    }

    protected open fun onHasPermissionsChanged(hasPermissions: Boolean) {
        // implemented by sub-classes
        logD(hasPermissions)
    }

    protected open fun getPermissionsToRequest(): Array<String> {
        return arrayOf()
    }

    protected fun setPermissionDeniedMessage(message: String) {
        permissionDeniedMessage = message
    }

    protected fun hasPermissions(): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    protected fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                onHasPermissionsChanged(true)
            } else {
                handlePermissionDenied()
            }
        }
    }

    private fun handlePermissionDenied() {
        if (shouldShowRequestPermissionRationale()) {
            showPermissionDeniedSnackbar()
        } else {
            showPermissionDeniedSettingsSnackbar()
        }
    }

    private fun shouldShowRequestPermissionRationale(): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true
            }
        }
        return false
    }

    private fun showPermissionDeniedSnackbar() {
        Snackbar.make(snackBarContainer, permissionDeniedMessage, Snackbar.LENGTH_SHORT)
            .setAction(R.string.action_grant) { requestPermissions() }
            .show()
    }

    private fun showPermissionDeniedSettingsSnackbar() {
        Snackbar.make(snackBarContainer, permissionDeniedMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction("Open Settings") { openApplicationSettings() }
            .show()
    }

    private fun openApplicationSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    companion object {
        const val PERMISSION_REQUEST = 100
    }
}
