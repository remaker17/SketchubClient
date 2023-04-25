package dev.remaker.sketchubx.models

data class AnnouncementResponse(
    val status: String,
    val statusCode: Int,
    val news: List<HashMap<String, String>>
)
