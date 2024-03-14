package com.flyjingfish.shapedrawable;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.flyjingfish.shapeimageviewlib.GraphicsDrawable;

public class CustomDrawableTarget extends CustomTarget<Drawable> {

    private ImageView mImageView;
    private GraphicsDrawable mGraphicsDrawable;

    public CustomDrawableTarget(GraphicsDrawable graphicsDrawable) {
        this.mGraphicsDrawable = graphicsDrawable;
        this.mImageView = graphicsDrawable.getImageView();
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        super.onLoadStarted(placeholder);
        mGraphicsDrawable.setDrawable(placeholder);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);
        mGraphicsDrawable.setDrawable(errorDrawable);
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        mGraphicsDrawable.setDrawable(resource);
        mImageView.setImageDrawable(mGraphicsDrawable);
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }
}
