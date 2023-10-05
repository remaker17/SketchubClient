package dev.remaker.sketchubx.core.ui.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dev.remaker.sketchubx.R;
import dev.remaker.sketchubx.core.ui.model.Project;
import dev.remaker.sketchubx.core.util.AndroidUtilities;

public class ProjectCell extends FrameLayout {

    protected TextView mTitleText;
    protected TextView mSubtitleText;
    protected TextView mDownloadsText;
    protected TextView mCommentsText;
    protected TextView mLikesText;

    protected ImageView mDownloadsIcon;
    protected ImageView mIcon;

    protected LinearLayout mLikesLinearView;

    public ProjectCell(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_project_cell, this);
        mTitleText = findViewById(R.id.title_text);
        mSubtitleText = findViewById(R.id.subtitle_text);
        mDownloadsText = findViewById(R.id.downloads_text);
        mCommentsText = findViewById(R.id.comments_text);
        mLikesText = findViewById(R.id.likes_text);
        mDownloadsIcon = findViewById(R.id.downloads_icon);
        mIcon = findViewById(R.id.icon);
        mLikesLinearView = findViewById(R.id.likes_linear);
        setPadding(0, AndroidUtilities.dp(8), 0, AndroidUtilities.dp(8));
    }

    public void bind(Project project, boolean hideDownloads) {
        setTitle(project.getTitle());
        setSubtitle(project.getUsername());

        mDownloadsText.setText(String.valueOf(project.getDownloadsCount()));
        mLikesLinearView.setVisibility(View.GONE);

        if (hideDownloads) {
            mDownloadsText.setVisibility(View.GONE);
            mDownloadsIcon.setVisibility(View.GONE);
        }

        if (project.getIconUrl() != null) {
            Glide.with(mIcon).load(project.getIconUrl()).centerCrop().into(mIcon);
        }
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

    public TextView getTitle() {
        return mTitleText;
    }

    public TextView getAuthorView() {
        return mSubtitleText;
    }

    public TextView getDownloadsText() {
        return mDownloadsText;
    }

    public ImageView getDownloadsIcon() {
        return mDownloadsIcon;
    }

    public TextView getCommentsText() {
        return mCommentsText;
    }

    public TextView getLikesText() {
        return mLikesText;
    }

    public LinearLayout getLikesLinear() {
        return mLikesLinearView;
    }

    public ImageView getIcon() {
        return mIcon;
    }
}
