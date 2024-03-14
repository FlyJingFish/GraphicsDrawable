package com.flyjingfish.shapedrawable;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flyjingfish.shapeimageviewlib.GraphicsDrawable;

public class GraphicsDrawableViewBackgroundTarget extends BaseViewTarget<Drawable> {

    private final GraphicsDrawable mGraphicsDrawable;
    private final GraphicsDrawable mStartGraphicsDrawable;
    private final GraphicsDrawable mFailedGraphicsDrawable;

    public GraphicsDrawableViewBackgroundTarget(GraphicsDrawable graphicsDrawable) {
        super(graphicsDrawable.getView());
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
