package dev.remaker.sketchubx.core.ui.cell

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import dev.remaker.sketchubx.R
import dev.remaker.sketchubx.core.util.ext.show
import dev.remaker.sketchubx.core.util.ext.updateMargin

class AnnouncementCell(context: Context) : FrameLayout(context) {

    private var title: TextView
    private var message: TextView
    private var timestamp: TextView
    private var image: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_announcement_item, this, true)
        title = findViewById(R.id.title)
        image = findViewById(R.id.image)
        message = findViewById(R.id.message)
        timestamp = findViewById(R.id.timestamp)
    }

    fun bind(hashMap: HashMap<String, String>) {
        title.text = hashMap["title"]
        message.text = hashMap["message"]
        timestamp.text = hashMap["timestamp"]
        val imageUrl = hashMap["image_url"]

        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context).load(imageUrl).into(image)
            image.show()
        }

        val topMargin = if (image.isVisible) {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8f,
                context.resources
                    .displayMetrics
            ).toInt()
        } else {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                12f,
                context.resources
                    .displayMetrics
            ).toInt()
        }
        title.updateMargin(top = topMargin)
    }
}
