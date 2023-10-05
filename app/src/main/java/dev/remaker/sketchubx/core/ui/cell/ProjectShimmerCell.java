package dev.remaker.sketchubx.core.ui.cell;

import android.content.Context;
import android.view.View;

import androidx.annotation.ColorInt;

import dev.remaker.sketchubx.R;
import dev.remaker.sketchubx.core.util.AndroidUtilities;
import dev.remaker.sketchubx.core.util.ext.ColorKt;

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
        @ColorInt int shimmerColor = ColorKt.resolveColor(getContext(), R.attr.m3ColorSurface);

        mIcon.setBackgroundColor(shimmerColor);
    }

    private void setupLikesLinearView() {
        mLikesLinearView.setVisibility(View.GONE);
    }

    private void setupPadding() {
        setPadding(mItemPadding, getPaddingTop(), mItemPadding, getPaddingBottom());
    }
}
