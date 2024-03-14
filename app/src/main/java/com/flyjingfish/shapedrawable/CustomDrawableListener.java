package com.flyjingfish.shapedrawable;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.flyjingfish.shapeimageviewlib.GraphicsDrawable;

public class CustomDrawableListener implements RequestListener<Drawable> {

    private ImageView mImageView;
    private GraphicsDrawable mGraphicsDrawable;

    public CustomDrawableListener(GraphicsDrawable graphicsDrawable) {
        this.mGraphicsDrawable = graphicsDrawable;
        this.mImageView = graphicsDrawable.getImageView();
    }


    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        mGraphicsDrawable.setDrawable(resource);
        mImageView.setImageDrawable(mGraphicsDrawable);
        return true;
    }
}
