package dev.remaker.sketchubx.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import dev.remaker.sketchubx.databinding.ActivityPermissionBinding
import dev.remaker.sketchubx.extensions.*
import dev.remaker.sketchubx.ui.activities.base.AbsBaseActivity
import dev.remaker.sketchubx.util.VersionUtils

class PermissionActivity : AbsBaseActivity() {
    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        setPermissionDeniedMessage("Please give permission to access the files.")
        setupStoragePermissionButton()
        setupFinishButton()
    }

    private fun setupStoragePermissionButton() {
        binding.storagePermission.setButtonClick {
            requestPermissions()
        }
        binding.storagePermission.checkImage.isVisible = hasStoragePermission()
    }

    private fun setupFinishButton() {
        binding.finish.isEnabled = hasStoragePermission()
        binding.finish.setOnClickListener {
            if (hasStoragePermission()) {
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
        finish()
    }

    override fun getPermissionsToRequest(): Array<String> {
        val permissions = mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!VersionUtils.hasR()) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        return permissions.toTypedArray()
    }

    override fun onHasPermissionsChanged(hasPermissions: Boolean) {
        binding.finish.isEnabled = hasPermissions
        binding.storagePermission.checkImage.isVisible = hasPermissions
    }

    private fun hasStoragePermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
