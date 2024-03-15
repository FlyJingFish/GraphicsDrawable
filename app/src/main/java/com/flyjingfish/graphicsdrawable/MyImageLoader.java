package com.flyjingfish.graphicsdrawable;

import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.flyjingfish.graphicsdrawableglidelib.GraphicsDrawableImageViewTarget;
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MyImageLoader {
    private MyImageLoader() {
    }

    private static MyImageLoader mInstance;

    public static MyImageLoader getInstance() {
        if (null == mInstance) {
            synchronized (MyImageLoader.class) {
                if (null == mInstance) {
                    mInstance = new MyImageLoader();
                }
            }
        }
        return mInstance;
    }

    public void load(ImageView iv, String url, @DrawableRes int p, @DrawableRes int err) {
        if (!ActivityCompatHelper.assertValidRequest(iv.getContext()))
            return;
        into(url, iv, 0, 0, p, err, false, -1, false);
    }

    public void loadRoundCorner(ImageView iv, String url, float radiusDp, @DrawableRes int p, @DrawableRes int err) {
        if (!ActivityCompatHelper.assertValidRequest(iv.getContext()))
            return;
        into(url, iv, 0, 0, p, err, false, radiusDp, false);
    }

    public void loadCircle(ImageView iv, String url,  @DrawableRes int p, @DrawableRes int err) {
        if (!ActivityCompatHelper.assertValidRequest(iv.getContext()))
            return;
        into(url, iv, 0, 0, p, err, true, -1, false);
    }
    private void into(String url, ImageView iv, int w, int h, @DrawableRes int p, @DrawableRes int err, boolean isCircle, float radiusDp, boolean isBlur) {
        into(url, iv, w, h, p, err, isCircle, radiusDp, isBlur,null);
    }
    private void into(String url,ImageView iv, int w, int h, @DrawableRes int p, @DrawableRes int err, boolean isCircle, float radiusDp, boolean isBlur, OnImageLoadListener requestListener) {
        RequestBuilder<Drawable> requestBuilder = GlideApp.with(iv).load(url);
        RequestOptions mRequestOptions = new RequestOptions();
        if (isBlur || isCircle || radiusDp != -1) {
            Transformation[] transformations = new Transformation[0];
            if (isBlur && !isCircle && radiusDp == -1) {
                transformations = new Transformation[]{new CenterCrop(), new BlurTransformation()};
            } else if (isBlur && isCircle && radiusDp == -1) {
                transformations = new Transformation[]{new CenterCrop(), new BlurTransformation(), new CircleCrop()};
            } else if (isBlur && !isCircle && radiusDp != -1) {
                transformations = new Transformation[]{new CenterCrop(), new BlurTransformation(), new RoundedCorners(dp2px(radiusDp))};
            } else if (!isBlur && isCircle && radiusDp == -1) {
                transformations = new Transformation[]{new CenterCrop(), new CircleCrop()};
            } else if (!isBlur && !isCircle && radiusDp != -1) {
                transformations = new Transformation[]{new CenterCrop(), new RoundedCorners(dp2px(radiusDp))};
            }
            mRequestOptions.transform(transformations);


            if (w > 0 && h > 0)
                mRequestOptions.override(w, h);
        } else if (w > 0 && h > 0) {
            requestBuilder.override(w, h);
        } else if (w == Target.SIZE_ORIGINAL && h == Target.SIZE_ORIGINAL) {
            requestBuilder.override(w, h);
        }
        if (p != -1)
            mRequestOptions.placeholder(p);
        if (err != -1)
            mRequestOptions.error(err);

        requestBuilder.apply(mRequestOptions);
        if (requestListener != null){
            requestBuilder.addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    requestListener.onFailed();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    requestListener.onSuccess();
                    return false;
                }
            });
        }


        GraphicsDrawable graphicsDrawable = new GraphicsDrawable(iv);
        if (isCircle || radiusDp>0){
            graphicsDrawable.setRadius(dp2px(radiusDp));
            graphicsDrawable.setShapeType(isCircle? GraphicsDrawable.ShapeType.OVAL: GraphicsDrawable.ShapeType.RECTANGLE);
        }
//        requestBuilder.into(new GraphicsDrawableViewBackgroundTarget(graphicsDrawable));
        requestBuilder.into(new GraphicsDrawableImageViewTarget(graphicsDrawable));
    }


    public interface OnImageLoadListener{
        void onSuccess();
        void onFailed();
    }


    public static int dp2px(float dp){
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,MyApplication.Companion.getMInstance().getResources().getDisplayMetrics()) + 0.5f);
    }
}
