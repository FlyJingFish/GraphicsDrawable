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
    private ShapeType mShapeType = ShapeType.CUSTOM;
    private final Path mDrawPath = new Path();
    private final Paint mImagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final PorterDuffXfermode SRC_IN = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private final View mView;
    private Drawable mDrawable;
    private Drawable mCustomDrawable;
    private final RectF mMatrixRectF = new RectF();
    private final Rect mMatrixRect = new Rect();
    private final Rect mDisplayRect = new Rect();
    private final RectF mDisplayRectF = new RectF();
    private final Matrix mDrawableMatrix = new Matrix();
    private float mStartTopRadius;
    private float mStartBottomRadius;
    private float mEndTopRadius;
    private float mEndBottomRadius;
    private float mLeftTopRadius;
    private float mLeftBottomRadius;
    private float mRightTopRadius;
    private float mRightBottomRadius;

    private boolean mFollowImageViewScaleType = true;
    private boolean mBackgroundMode = false;

    private ImageView.ScaleType mScaleType;

    private boolean mUseViewPadding = true;
    public GraphicsDrawable(View view) {
        this.mView = view;
        mCustomDrawable = ContextCompat.getDrawable(view.getContext(),R.drawable.ic_vector_heart);
        isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
    }



    public View getView() {
        return mView;
    }

    public ImageView.ScaleType getScaleType(){
        if (mFollowImageViewScaleType && mView instanceof ImageView){
            return ((ImageView) mView).getScaleType();
        }else if (mScaleType != null){
            return mScaleType;
        }else {
            return ImageView.ScaleType.FIT_XY;
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mDrawable == null){
            return;
        }
        updateDrawableMatrix(mDrawable);

        mDrawPath.reset();
        if (mShapeType == ShapeType.OVAL) {
            mDrawPath.addOval(mDisplayRectF, Path.Direction.CCW);
        } else if (mShapeType == ShapeType.RECTANGLE) {
            float leftTopRadius = ViewUtils.getRtlValue(isRtl ? mEndTopRadius : mStartTopRadius, this.mLeftTopRadius);
            float rightTopRadius = ViewUtils.getRtlValue(isRtl ? mStartTopRadius : mEndTopRadius, this.mRightTopRadius);
            float rightBottomRadius = ViewUtils.getRtlValue(isRtl ? mStartBottomRadius : mEndBottomRadius, this.mRightBottomRadius);
            float leftBottomRadius = ViewUtils.getRtlValue(isRtl ? mEndBottomRadius : mStartBottomRadius, this.mLeftBottomRadius);
            float[] radius = new float[]{leftTopRadius,leftTopRadius,rightTopRadius,rightTopRadius,rightBottomRadius,rightBottomRadius,leftBottomRadius,leftBottomRadius};

            mDrawPath.addRoundRect(mDisplayRectF, radius, Path.Direction.CCW);
        }

        if (mShapeType == ShapeType.OVAL || mShapeType == ShapeType.RECTANGLE) {
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            canvas.drawPath(mDrawPath, mImagePaint);
            mImagePaint.setXfermode(SRC_IN);
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            mImagePaint.setXfermode(null);
        } else if (mShapeType == ShapeType.CUSTOM){
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            mCustomDrawable.setBounds(mDisplayRect);
            mCustomDrawable.draw(canvas);
            mImagePaint.setXfermode(SRC_IN);
            canvas.saveLayer(new RectF(0,0,canvas.getWidth(),canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
            mImagePaint.setXfermode(null);
        }
        mDrawable.setBounds(mMatrixRect);
        mDrawable.draw(canvas);
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
    private int getImageViewWidth(View view) {
        if (mBackgroundMode){
            return view.getWidth();
        }else {
            return view.getWidth() - ViewUtils.getViewPaddingLeft(view) - ViewUtils.getViewPaddingRight(view);
        }
    }

    private int getImageViewHeight(View view) {
        if (mBackgroundMode){
            return view.getHeight();
        }else {
            return view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
        }
    }
    private void updateDrawableMatrix(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        ImageView.ScaleType scaleType = getScaleType();
        final float viewWidth = getImageViewWidth(mView);
        final float viewHeight = getImageViewHeight(mView);
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
        mMatrixRectF.set(0, 0, mDrawable.getIntrinsicWidth(),
                mDrawable.getIntrinsicHeight());
        mDrawableMatrix.mapRect(mMatrixRectF);
        mMatrixRect.set((int) mMatrixRectF.left, (int) mMatrixRectF.top, (int) mMatrixRectF.right, (int) mMatrixRectF.bottom);
    }
    private void getDisplayRect() {
        final float viewWidth = getImageViewWidth(mView);
        final float viewHeight = getImageViewHeight(mView);
        float left;
        float top;
        float right;
        float bottom;
        if (mBackgroundMode){
            left = mMatrixRectF.left;
            top = mMatrixRectF.top;
            right = mMatrixRectF.right;
            bottom = mMatrixRectF.bottom;
        }else {
            float paddingLeft = getViewPaddingLeft();
            float paddingTop =getViewPaddingTop();
            float paddingRight = getViewPaddingRight();
            float paddingBottom = getViewPaddingBottom();
            left = Math.max(mMatrixRectF.left, paddingLeft);
            top = Math.max(mMatrixRectF.top, paddingTop);
            right = Math.min(mMatrixRectF.right, viewWidth - paddingRight);
            bottom = Math.min(mMatrixRectF.bottom, viewHeight - paddingBottom);
        }

        mDisplayRect.set((int) left, (int) top, (int) right, (int) bottom);
        mDisplayRectF.set((int) left, (int) top, (int) right, (int) bottom);
    }


    private int getViewPaddingLeft(){
        if (mUseViewPadding){
            return 0;
        }else {
            return ViewUtils.getViewPaddingLeft(mView);
        }
    }

    private int getViewPaddingRight(){
        if (mUseViewPadding){
            return 0;
        }else {
            return ViewUtils.getViewPaddingRight(mView);
        }
    }


    private int getViewPaddingTop(){
        if (mUseViewPadding){
            return 0;
        }else {
            return mView.getPaddingTop();
        }
    }

    private int getViewPaddingBottom(){
        if (mUseViewPadding){
            return 0;
        }else {
            return mView.getPaddingBottom();
        }
    }

    public Drawable getCustomDrawable() {
        return mCustomDrawable;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }
    public void setDrawable(@DrawableRes int drawableRes) {
        setDrawable(ContextCompat.getDrawable(mView.getContext(),drawableRes));
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }

    public void setCustomDrawable(Drawable customDrawable) {
        this.mCustomDrawable = customDrawable;
    }
    public void setScaleType(ImageView.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    public void setBackgroundMode(boolean backgroundMode) {
        this.mBackgroundMode = backgroundMode;
    }

    public void setFollowImageViewScaleType(boolean followImageViewScaleType) {
        this.mFollowImageViewScaleType = followImageViewScaleType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.mShapeType = shapeType;
    }

    public void setRadius(float radius) {
        this.mLeftTopRadius = radius;
        this.mRightTopRadius = radius;
        this.mLeftBottomRadius = radius;
        this.mRightBottomRadius = radius;
        invalidateSelf();
    }

    public void setRadius(float leftTopRadius,float rightTopRadius,float rightBottomRadius,float leftBottomRadius) {
        this.mLeftTopRadius = leftTopRadius;
        this.mRightTopRadius = rightTopRadius;
        this.mRightBottomRadius = rightBottomRadius;
        this.mLeftBottomRadius = leftBottomRadius;
        invalidateSelf();
    }
    public void setRelativeRadius(float startTopRadius,float endTopRadius,float endBottomRadius,float startBottomRadius) {
        this.mStartTopRadius = startTopRadius;
        this.mEndTopRadius = endTopRadius;
        this.mEndBottomRadius = endBottomRadius;
        this.mStartBottomRadius = startBottomRadius;
        invalidateSelf();
    }

    public void setUseViewPadding(boolean useViewPadding) {
        this.mUseViewPadding = useViewPadding;
    }



    public GraphicsDrawable copy(){
        GraphicsDrawable graphicsDrawable = new GraphicsDrawable(mView);
        graphicsDrawable.setRadius(mLeftTopRadius, mRightTopRadius, mRightBottomRadius, mLeftBottomRadius);
        graphicsDrawable.setRelativeRadius(mStartTopRadius, mEndTopRadius, mEndBottomRadius, mStartBottomRadius);
        graphicsDrawable.setShapeType(mShapeType);
        graphicsDrawable.setCustomDrawable(mCustomDrawable);
        graphicsDrawable.setScaleType(mScaleType);
        graphicsDrawable.setFollowImageViewScaleType(mFollowImageViewScaleType);
        graphicsDrawable.setBackgroundMode(mBackgroundMode);
        graphicsDrawable.setUseViewPadding(mUseViewPadding);
        return graphicsDrawable;
    }
}
