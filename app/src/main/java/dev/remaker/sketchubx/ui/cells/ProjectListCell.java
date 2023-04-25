package dev.remaker.sketchubx.ui.cells;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dev.remaker.sketchubx.R;
import dev.remaker.sketchubx.models.Project;

public class ProjectListCell extends FrameLayout {

    private TextView mTitleText;
    private TextView mSubtitleText;
    private TextView mAuthorText;
    private TextView mDownloadsText;
    private TextView mCommentsText;
    private TextView mLikesText;

    private ImageView mDownloadsIcon;
    private ImageView mIcon;

    public ProjectListCell(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_project_list_cell, this);
        mTitleText = findViewById(R.id.title_text);
        mAuthorText = findViewById(R.id.author_text);
        mSubtitleText = findViewById(R.id.subtitle_text);
        mDownloadsText = findViewById(R.id.downloads_text);
        mCommentsText = findViewById(R.id.comments_text);
        mLikesText = findViewById(R.id.likes_text);
        mDownloadsIcon = findViewById(R.id.downloads_icon);
        mIcon = findViewById(R.id.icon);
    }

    public void bind(Project project) {
        setTitle(project.getTitle());
        setAuthor(project.getUsername());

        if (project.getIconUrl() != null) {
            Glide.with(mIcon).load(project.getIconUrl()).centerCrop().into(mIcon);
        }

        mLikesText.setText(String.valueOf(project.getLikes()));
        mDownloadsText.setText(String.valueOf(project.getDownloadsCount()));
        mCommentsText.setText(String.valueOf(project.getCommentsCount()));
    }

    public void setTitle(String title) {
        if (title == null) {
            title = "Unknown";
        }
        mTitleText.setText(title);
    }

    public void setSubtitle(String subtitle) {
        if (subtitle == null) {
            subtitle = "Unknown user";
        }
        mSubtitleText.setText(subtitle);
    }

    public void setAuthor(String author) {
        mAuthorText.setText(author);
    }

    public TextView getDownloadsText() {
        return mDownloadsText;
    }

    public ImageView getDownloadsIcon() {
        return mDownloadsIcon;
    }

    public ImageView getIcon() {
        return mIcon;
    }

    public TextView getCommentsText() {
        return mCommentsText;
    }

    public TextView getLikesText() {
        return mLikesText;
    }
}
