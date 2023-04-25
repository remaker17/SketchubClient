package dev.remaker.sketchubx.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.materialDialog(title: Int): MaterialAlertDialogBuilder {
    return MaterialAlertDialogBuilder(
        requireContext()
    ).setTitle(title)
}
