package com.flyjingfish.graphicsdrawableglidelib;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable;

public class GraphicsDrawableViewBackgroundTarget extends BaseViewTarget<Drawable> {

    private final GraphicsDrawable mGraphicsDrawable;
    private final GraphicsDrawable mStartGraphicsDrawable;
    private final GraphicsDrawable mFailedGraphicsDrawable;

    public GraphicsDrawableViewBackgroundTarget(GraphicsDrawable graphicsDrawable) {
        super(graphicsDrawable.getView());
        graphicsDrawable.setFollowImageViewScaleType(false);
        graphicsDrawable.setBackgroundMode(true);
        this.mGraphicsDrawable = graphicsDrawable;
        mStartGraphicsDrawable = graphicsDrawable.copy();
        mFailedGraphicsDrawable = graphicsDrawable.copy();
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        mStartGraphicsDrawable.setDrawable(placeholder);
        super.onLoadStarted(mStartGraphicsDrawable);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        mFailedGraphicsDrawable.setDrawable(errorDrawable);
        super.onLoadFailed(mFailedGraphicsDrawable);
    }

    @Override
    protected void setResource(@Nullable Drawable resource) {
        mGraphicsDrawable.setDrawable(resource);
        view.setBackground(mGraphicsDrawable);
    }



}
