package com.flyjingfish.shapedrawable;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.ImageViewTarget;
import com.flyjingfish.shapeimageviewlib.GraphicsDrawable;

public class GraphicsDrawableImageViewTarget extends ImageViewTarget<Drawable> {

    private final GraphicsDrawable mGraphicsDrawable;
    private final GraphicsDrawable mStartGraphicsDrawable;
    private final GraphicsDrawable mFailedGraphicsDrawable;

    public GraphicsDrawableImageViewTarget(GraphicsDrawable graphicsDrawable) {
        super((ImageView) graphicsDrawable.getView());
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
        view.setImageDrawable(mGraphicsDrawable);
    }

}
