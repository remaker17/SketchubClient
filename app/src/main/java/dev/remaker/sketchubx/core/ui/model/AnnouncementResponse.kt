package dev.remaker.sketchubx.core.ui.model

data class AnnouncementResponse(
    val status: String,
    val statusCode: Int,
    val news: List<HashMap<String, String>>
)
