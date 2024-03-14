package com.flyjingfish.shapeimageviewlib;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.LayoutDirection;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.text.TextUtilsCompat;

import java.util.Locale;

public class GraphicsDrawable extends Drawable {
    private final boolean isRtl;
    private ShapeType shapeType = ShapeType.CUSTOM;
    private final Path mDrawPath = new Path();
    private final Paint mImagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final PorterDuffXfermode SRC_IN = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private final ImageView mImageView;
    private Drawable drawable;
    private Drawable customDrawable;
    private final RectF mMatrixRectF = new RectF();
    private final Rect mMatrixRect = new Rect();
    private final Rect mDisplayRect = new Rect();
    private final RectF mDisplayRectF = new RectF();
    private final Matrix mDrawableMatrix = new Matrix();
    private float startTopRadius;
    private float startBottomRadius;
    private float endTopRadius;
    private float endBottomRadius;
    private float leftTopRadius;
    private float leftBottomRadius;
    private float rightTopRadius;
    private float rightBottomRadius;

    public GraphicsDrawable(ImageView view) {
        this.mImageView = view;
        customDrawable = ContextCompat.getDrawable(view.getContext(),R.drawable.ic_vector_heart);
        isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
    }

    public void setDrawable(@DrawableRes int drawableRes) {
        setDrawable(ContextCompat.getDrawable(mImageView.getContext(),drawableRes));
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public ImageView.ScaleType getScaleType(){
        return mImageView.getScaleType();
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (drawable == null){
            return;
        }
        updateDrawableMatrix(drawable);

        mDrawPath.reset();
        if (shapeType == ShapeType.OVAL) {
            mDrawPath.addOval(mDisplayRectF, Path.Direction.CCW);
        } else if (shapeType == ShapeType.RECTANGLE) {
            float leftTopRadius = ViewUtils.getRtlValue(isRtl ? endTopRadius : startTopRadius, this.leftTopRadius);
            float rightTopRadius = ViewUtils.getRtlValue(isRtl ? startTopRadius : endTopRadius, this.rightTopRadius);
            float rightBottomRadius = ViewUtils.getRtlValue(isRtl ? startBottomRadius : endBottomRadius, this.rightBottomRadius);
            float leftBottomRadius = ViewUtils.getRtlValue(isRtl ? endBottomRadius : startBottomRadius, this.leftBottomRadius);
            float[] radius = new float[]{leftTopRadius,leftTopRadius,rightTopRadius,rightTopRadius,rightBottomRadius,rightBottomRadius,leftBottomRadius,leftBottomRadius};

            mDrawPath.addRoundRect(mDisplayRectF, radius, Path.Direction.CCW);
        }

        if (shapeType == ShapeType.OVAL || shapeType == ShapeType.RECTANGLE) {
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            canvas.drawPath(mDrawPath, mImagePaint);
            mImagePaint.setXfermode(SRC_IN);
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            mImagePaint.setXfermode(null);
        } else if (shapeType == ShapeType.CUSTOM){
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            customDrawable.setBounds(mDisplayRect);
            customDrawable.draw(canvas);
            mImagePaint.setXfermode(SRC_IN);
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            mImagePaint.setXfermode(null);
        }
        drawable.setBounds(mMatrixRect);
        drawable.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        int oldAlpha = mImagePaint.getAlpha();
        if (oldAlpha != alpha){
            mImagePaint.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mImagePaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public enum ShapeType {
        NONE, RECTANGLE, OVAL, CUSTOM;
    }
    private int getImageViewWidth(ImageView imageView) {
        return imageView.getWidth() - ViewUtils.getViewPaddingLeft(imageView) - ViewUtils.getViewPaddingRight(imageView);
    }

    private int getImageViewHeight(ImageView imageView) {
        return imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();
    }
    private void updateDrawableMatrix(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        ImageView.ScaleType scaleType = getScaleType();
        final float viewWidth = getImageViewWidth(mImageView);
        final float viewHeight = getImageViewHeight(mImageView);
        final int drawableWidth = drawable.getIntrinsicWidth();
        final int drawableHeight = drawable.getIntrinsicHeight();
        mDrawableMatrix.reset();
        final float widthScale = viewWidth / drawableWidth;
        final float heightScale = viewHeight / drawableHeight;
        if (scaleType == ImageView.ScaleType.CENTER) {
            mDrawableMatrix.postTranslate((viewWidth - drawableWidth) / 2F,
                    (viewHeight - drawableHeight) / 2F);

        } else if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            float scale = Math.max(widthScale, heightScale);
            mDrawableMatrix.postScale(scale, scale);
            mDrawableMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else if (scaleType == ImageView.ScaleType.CENTER_INSIDE) {
            float scale = Math.min(1.0f, Math.min(widthScale, heightScale));
            mDrawableMatrix.postScale(scale, scale);
            mDrawableMatrix.postTranslate((viewWidth - drawableWidth * scale) / 2F,
                    (viewHeight - drawableHeight * scale) / 2F);

        } else {
            RectF mTempSrc = new RectF(0, 0, drawableWidth, drawableHeight);
            RectF mTempDst = new RectF(0, 0, viewWidth, viewHeight);
            switch (scaleType) {
                case FIT_CENTER:
                    mDrawableMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.CENTER);
                    break;
                case FIT_START:
                    mDrawableMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.START);
                    break;
                case FIT_END:
                    mDrawableMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.END);
                    break;
                case FIT_XY:
                    mDrawableMatrix.setRectToRect(mTempSrc, mTempDst, Matrix.ScaleToFit.FILL);
                    break;
                default:
                    break;
            }
        }
        getDrawableRect();
        getDisplayRect();
    }
    private void getDrawableRect() {
        mMatrixRectF.set(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        mDrawableMatrix.mapRect(mMatrixRectF);
        mMatrixRect.set((int) mMatrixRectF.left, (int) mMatrixRectF.top, (int) mMatrixRectF.right, (int) mMatrixRectF.bottom);
    }
    private void getDisplayRect() {
        final float viewWidth = getImageViewWidth(mImageView);
        final float viewHeight = getImageViewHeight(mImageView);
        float paddingLeft = getViewPaddingLeft();
        float paddingTop =getViewPaddingTop();
        float paddingRight = getViewPaddingRight();
        float paddingBottom = getViewPaddingBottom();

        float left = Math.max(mMatrixRectF.left, paddingLeft);
        float top = Math.max(mMatrixRectF.top, paddingTop);
        float right = Math.min(mMatrixRectF.right, viewWidth - paddingRight);
        float bottom = Math.min(mMatrixRectF.bottom, viewHeight - paddingBottom);

        mDisplayRect.set((int) left, (int) top, (int) right, (int) bottom);
        mDisplayRectF.set((int) left, (int) top, (int) right, (int) bottom);
    }

    boolean useViewPadding = true;
    public int getViewPaddingLeft(){
        if (useViewPadding){
            return 0;
        }else {
            return ViewUtils.getViewPaddingLeft(mImageView);
        }
    }

    public int getViewPaddingRight(){
        if (useViewPadding){
            return 0;
        }else {
            return ViewUtils.getViewPaddingRight(mImageView);
        }
    }


    public int getViewPaddingTop(){
        if (useViewPadding){
            return 0;
        }else {
            return mImageView.getPaddingTop();
        }
    }

    public int getViewPaddingBottom(){
        if (useViewPadding){
            return 0;
        }else {
            return mImageView.getPaddingBottom();
        }
    }


    public void setRadius(float radius) {
        this.leftTopRadius = radius;
        this.rightTopRadius = radius;
        this.leftBottomRadius = radius;
        this.rightBottomRadius = radius;
        invalidateSelf();
    }

    public void setRadius(float leftTopRadius,float rightTopRadius,float rightBottomRadius,float leftBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.rightBottomRadius = rightBottomRadius;
        this.leftBottomRadius = leftBottomRadius;
        invalidateSelf();
    }
    public void setRelativeRadius(float startTopRadius,float endTopRadius,float endBottomRadius,float startBottomRadius) {
        this.startTopRadius = startTopRadius;
        this.endTopRadius = endTopRadius;
        this.endBottomRadius = endBottomRadius;
        this.startBottomRadius = startBottomRadius;
        invalidateSelf();
    }

}
