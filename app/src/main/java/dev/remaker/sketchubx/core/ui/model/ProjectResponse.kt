package dev.remaker.sketchubx.core.ui.model

import com.google.gson.annotations.SerializedName

data class ProjectResponse(
    val status: String,
    @SerializedName("status_code")
    val statusCode: Int,
    val message: String,
    val projects: ArrayList<Project>?,
    @SerializedName("total_pages")
    val totalPages: String
) {
    companion object {
        const val SUCCESS = "success"
    }

    fun totalPagesAsInt(): Int {
        return totalPages.toInt()
    }
}
