package com.flyjingfish.graphicsdrawablelib

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.LayoutDirection
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.text.TextUtilsCompat
import java.util.Locale

class GraphicsDrawable(val view: View) : Drawable() {
    private val isRtl: Boolean = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL
    private var mShapeType = ShapeType.NONE
    private val mDrawPath = Path()
    private val mImagePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val SRC_IN = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    var drawable: Drawable? = null
        private set
    var customDrawable: Drawable? = null
    private val mDrawRectF = RectF()
    private val mMatrixRectF = RectF()
    private val mMatrixRect = Rect()
    private val mDisplayRect = Rect()
    private val mDisplayRectF = RectF()
    private val mDrawableMatrix = Matrix()
    private var mStartTopRadius = 0f
    private var mStartBottomRadius = 0f
    private var mEndTopRadius = 0f
    private var mEndBottomRadius = 0f
    private var mLeftTopRadius = 0f
    private var mLeftBottomRadius = 0f
    private var mRightTopRadius = 0f
    private var mRightBottomRadius = 0f
    private var mFollowImageViewScaleType = true
    private var mBackgroundMode = false
    private var mScaleType: ScaleType? = null
    private var mUseViewPadding = true
    private val mTempSrc = RectF()
    private val mTempDst = RectF()

    fun getScaleType(): ScaleType {
        return if (mFollowImageViewScaleType && view is ImageView) {
            view.scaleType
        } else mScaleType ?: ScaleType.FIT_XY
    }

    override fun draw(canvas: Canvas) {
        val drawable = this.drawable ?: return

        updateDrawableMatrix(drawable)
        mDrawPath.reset()
        if (mShapeType == ShapeType.OVAL) {
            mDrawPath.addOval(mDisplayRectF, Path.Direction.CCW)
        } else if (mShapeType == ShapeType.RECTANGLE) {
            val leftTopRadius = ViewUtils.getRtlValue(
                if (isRtl) mEndTopRadius else mStartTopRadius,
                mLeftTopRadius
            )
            val rightTopRadius = ViewUtils.getRtlValue(
                if (isRtl) mStartTopRadius else mEndTopRadius,
                mRightTopRadius
            )
            val rightBottomRadius = ViewUtils.getRtlValue(
                if (isRtl) mStartBottomRadius else mEndBottomRadius,
                mRightBottomRadius
            )
            val leftBottomRadius = ViewUtils.getRtlValue(
                if (isRtl) mEndBottomRadius else mStartBottomRadius,
                mLeftBottomRadius
            )
            val radius = floatArrayOf(
                leftTopRadius,
                leftTopRadius,
                rightTopRadius,
                rightTopRadius,
                rightBottomRadius,
                rightBottomRadius,
                leftBottomRadius,
                leftBottomRadius
            )
            mDrawPath.addRoundRect(mDisplayRectF, radius, Path.Direction.CCW)
        }
        mDrawRectF[0f, 0f, view.width.toFloat()] = view.height.toFloat()
        if (mShapeType == ShapeType.OVAL || mShapeType == ShapeType.RECTANGLE) {
            canvas.saveLayer(mDrawRectF, mImagePaint, Canvas.ALL_SAVE_FLAG)
            canvas.drawPath(mDrawPath, mImagePaint)
            mImagePaint.xfermode = SRC_IN
            canvas.saveLayer(mDrawRectF, mImagePaint, Canvas.ALL_SAVE_FLAG)
            mImagePaint.xfermode = null
        } else if (mShapeType == ShapeType.CUSTOM) {
            canvas.saveLayer(mDrawRectF, mImagePaint, Canvas.ALL_SAVE_FLAG)
            customDrawable!!.bounds = mDisplayRect
            customDrawable!!.draw(canvas)
            mImagePaint.xfermode = SRC_IN
            canvas.saveLayer(mDrawRectF, mImagePaint, Canvas.ALL_SAVE_FLAG)
            mImagePaint.xfermode = null
        }
        drawable.bounds = mMatrixRect
        drawable.draw(canvas)
    }

    override fun setAlpha(alpha: Int) {
        val oldAlpha = mImagePaint.alpha
        if (oldAlpha != alpha) {
            mImagePaint.alpha = alpha
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mImagePaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    enum class ShapeType {
        NONE, RECTANGLE, OVAL, CUSTOM
    }

    private fun getImageViewWidth(view: View): Int {
        return if (mBackgroundMode) {
            view.width
        } else {
            view.width - ViewUtils.getViewPaddingLeft(view) - ViewUtils.getViewPaddingRight(view)
        }
    }

    private fun getImageViewHeight(view: View): Int {
        return if (mBackgroundMode) {
            view.height
        } else {
            view.height - view.paddingTop - view.paddingBottom
        }
    }

    private fun updateDrawableMatrix(drawable: Drawable?) {
        if (drawable == null) {
            return
        }
        val scaleType = getScaleType()
        val viewWidth = getImageViewWidth(view).toFloat()
        val viewHeight = getImageViewHeight(view).toFloat()
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        mDrawableMatrix.reset()
        val widthScale = viewWidth / drawableWidth
        val heightScale = viewHeight / drawableHeight
        if (scaleType == ScaleType.CENTER) {
            mDrawableMatrix.postTranslate(
                (viewWidth - drawableWidth) / 2f,
                (viewHeight - drawableHeight) / 2f
            )
        } else if (scaleType == ScaleType.CENTER_CROP) {
            val scale = Math.max(widthScale, heightScale)
            mDrawableMatrix.postScale(scale, scale)
            mDrawableMatrix.postTranslate(
                (viewWidth - drawableWidth * scale) / 2f,
                (viewHeight - drawableHeight * scale) / 2f
            )
        } else if (scaleType == ScaleType.CENTER_INSIDE) {
            val scale = Math.min(1.0f, Math.min(widthScale, heightScale))
            mDrawableMatrix.postScale(scale, scale)
            mDrawableMatrix.postTranslate(
                (viewWidth - drawableWidth * scale) / 2f,
                (viewHeight - drawableHeight * scale) / 2f
            )
        } else {
            mTempSrc[0f, 0f, drawableWidth.toFloat()] = drawableHeight.toFloat()
            mTempDst[0f, 0f, viewWidth] = viewHeight
            when (scaleType) {
                ScaleType.FIT_CENTER -> mDrawableMatrix.setRectToRect(
                    mTempSrc,
                    mTempDst,
                    Matrix.ScaleToFit.CENTER
                )

                ScaleType.FIT_START -> mDrawableMatrix.setRectToRect(
                    mTempSrc,
                    mTempDst,
                    Matrix.ScaleToFit.START
                )

                ScaleType.FIT_END -> mDrawableMatrix.setRectToRect(
                    mTempSrc,
                    mTempDst,
                    Matrix.ScaleToFit.END
                )

                ScaleType.FIT_XY -> mDrawableMatrix.setRectToRect(
                    mTempSrc,
                    mTempDst,
                    Matrix.ScaleToFit.FILL
                )

                else -> {}
            }
        }
        getDrawableRect()
        getDisplayRect()
    }

    private fun getDrawableRect() {
        val drawable = this.drawable ?: return
        mMatrixRectF[0f, 0f, drawable.intrinsicWidth.toFloat()] =
            drawable.intrinsicHeight.toFloat()
        mDrawableMatrix.mapRect(mMatrixRectF)
        mMatrixRect[mMatrixRectF.left.toInt(), mMatrixRectF.top.toInt(), mMatrixRectF.right.toInt()] =
            mMatrixRectF.bottom.toInt()
    }

    private fun getDisplayRect() {
        val viewWidth = getImageViewWidth(view).toFloat()
        val viewHeight = getImageViewHeight(view).toFloat()
        val left: Float
        val top: Float
        val right: Float
        val bottom: Float
        if (mBackgroundMode) {
            left = mMatrixRectF.left
            top = mMatrixRectF.top
            right = mMatrixRectF.right
            bottom = mMatrixRectF.bottom
        } else {
            val paddingLeft = getViewPaddingLeft().toFloat()
            val paddingTop = getViewPaddingTop().toFloat()
            val paddingRight = getViewPaddingRight().toFloat()
            val paddingBottom = getViewPaddingBottom().toFloat()
            left = Math.max(mMatrixRectF.left, paddingLeft)
            top = Math.max(mMatrixRectF.top, paddingTop)
            right = Math.min(mMatrixRectF.right, viewWidth - paddingRight)
            bottom = Math.min(mMatrixRectF.bottom, viewHeight - paddingBottom)
        }
        mDisplayRect[left.toInt(), top.toInt(), right.toInt()] = bottom.toInt()
        mDisplayRectF[left.toInt().toFloat(), top.toInt().toFloat(), right.toInt().toFloat()] =
            bottom.toInt().toFloat()
    }
    private fun getViewPaddingLeft(): Int {
        return if (mUseViewPadding) {
            0
        } else {
            ViewUtils.getViewPaddingLeft(view)
        }
    }

    private fun getViewPaddingRight(): Int {
        return if (mUseViewPadding) {
            0
        } else {
            ViewUtils.getViewPaddingRight(view)
        }
    }


    private fun getViewPaddingTop(): Int {
        return if (mUseViewPadding) {
            0
        } else {
            view.getPaddingTop()
        }
    }

    private fun getViewPaddingBottom(): Int {
        return if (mUseViewPadding) {
            0
        } else {
            view.getPaddingBottom()
        }
    }

    fun setDrawable(@DrawableRes drawableRes: Int) {
        setDrawable(ContextCompat.getDrawable(view.context, drawableRes))
    }

    fun setDrawable(drawable: Drawable?) {
        this.drawable = drawable
    }

    fun setBackgroundMode(backgroundMode: Boolean) {
        mBackgroundMode = backgroundMode
    }

    fun setFollowImageViewScaleType(followImageViewScaleType: Boolean) {
        mFollowImageViewScaleType = followImageViewScaleType
    }

    fun setShapeType(shapeType: ShapeType) {
        mShapeType = shapeType
    }

    fun setRadius(radius: Float) {
        mLeftTopRadius = radius
        mRightTopRadius = radius
        mLeftBottomRadius = radius
        mRightBottomRadius = radius
        invalidateSelf()
    }

    fun setRadius(
        leftTopRadius: Float,
        rightTopRadius: Float,
        rightBottomRadius: Float,
        leftBottomRadius: Float
    ) {
        mLeftTopRadius = leftTopRadius
        mRightTopRadius = rightTopRadius
        mRightBottomRadius = rightBottomRadius
        mLeftBottomRadius = leftBottomRadius
        invalidateSelf()
    }

    fun setRelativeRadius(
        startTopRadius: Float,
        endTopRadius: Float,
        endBottomRadius: Float,
        startBottomRadius: Float
    ) {
        mStartTopRadius = startTopRadius
        mEndTopRadius = endTopRadius
        mEndBottomRadius = endBottomRadius
        mStartBottomRadius = startBottomRadius
        invalidateSelf()
    }

    fun setUseViewPadding(useViewPadding: Boolean) {
        mUseViewPadding = useViewPadding
    }

    fun copy(): GraphicsDrawable {
        val graphicsDrawable = GraphicsDrawable(
            view
        )
        graphicsDrawable.setRadius(
            mLeftTopRadius,
            mRightTopRadius,
            mRightBottomRadius,
            mLeftBottomRadius
        )
        graphicsDrawable.setRelativeRadius(
            mStartTopRadius,
            mEndTopRadius,
            mEndBottomRadius,
            mStartBottomRadius
        )
        graphicsDrawable.setShapeType(mShapeType)
        graphicsDrawable.customDrawable = customDrawable
        graphicsDrawable.mScaleType = mScaleType
        graphicsDrawable.setFollowImageViewScaleType(mFollowImageViewScaleType)
        graphicsDrawable.setBackgroundMode(mBackgroundMode)
        graphicsDrawable.setUseViewPadding(mUseViewPadding)
        return graphicsDrawable
    }
}