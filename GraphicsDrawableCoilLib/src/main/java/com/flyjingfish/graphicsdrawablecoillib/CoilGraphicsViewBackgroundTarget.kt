package com.flyjingfish.graphicsdrawablecoillib

import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.target.ImageViewTarget
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable

open class CoilGraphicsViewBackgroundTarget(view: ImageView, private val graphicsDrawable: GraphicsDrawable) : ViewBackgroundTarget(view) {
    private val mGraphicsDrawable: GraphicsDrawable
    private val mStartGraphicsDrawable: GraphicsDrawable
    private val mFailedGraphicsDrawable: GraphicsDrawable
    init {
        graphicsDrawable.setBackgroundMode(true)
        mGraphicsDrawable = graphicsDrawable
        mStartGraphicsDrawable = graphicsDrawable.copy()
        mFailedGraphicsDrawable = graphicsDrawable.copy()
    }
    override fun onStart(placeholder: Drawable?) {
        mStartGraphicsDrawable.setDrawable(placeholder)
        super.onStart(mStartGraphicsDrawable)
    }

    override fun onError(error: Drawable?) {
        mFailedGraphicsDrawable.setDrawable(error)
        super.onError(mFailedGraphicsDrawable)
    }

    override fun onSuccess(result: Drawable) {
        mGraphicsDrawable.setDrawable(result)
        super.onSuccess(mGraphicsDrawable)
    }
}