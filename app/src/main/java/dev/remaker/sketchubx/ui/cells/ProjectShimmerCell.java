package dev.remaker.sketchubx.ui.cells;

import android.content.Context;
import android.view.View;

import androidx.annotation.ColorInt;

import dev.remaker.sketchubx.R;
import dev.remaker.sketchubx.extensions.ColorExtensionsKt;
import dev.remaker.sketchubx.util.AndroidUtilities;

public class ProjectShimmerCell extends ProjectCell {

    private int mItemPadding = AndroidUtilities.getProperPadding() / 2;

    public ProjectShimmerCell(Context context) {
        super(context);
        init();
    }

    private void init() {
        setupTitleView();
        setupIconView();
        setupLikesLinearView();
        setupPadding();
    }

    private void setupTitleView() {
        mTitleText.setBackgroundDrawable(
                getContext().getDrawable(R.drawable.project_shimmer_title_item));
    }

    private void setupIconView() {
        @ColorInt int shimmerColor = ColorExtensionsKt.resolveColor(getContext(), R.attr.colorSurfaceVariant);

        mIcon.setBackgroundColor(shimmerColor);
    }

    private void setupLikesLinearView() {
        mLikesLinearView.setVisibility(View.GONE);
    }

    private void setupPadding() {
        setPadding(mItemPadding, getPaddingTop(), mItemPadding, getPaddingBottom());
    }
}
