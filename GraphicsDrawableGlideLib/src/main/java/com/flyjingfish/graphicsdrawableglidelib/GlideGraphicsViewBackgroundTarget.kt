package com.flyjingfish.graphicsdrawableglidelib

import android.graphics.drawable.Drawable
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable

open class GlideGraphicsViewBackgroundTarget(graphicsDrawable: GraphicsDrawable) :
    BaseViewTarget<Drawable>(graphicsDrawable.view) {
    private val mGraphicsDrawable: GraphicsDrawable
    private val mStartGraphicsDrawable: GraphicsDrawable
    private val mFailedGraphicsDrawable: GraphicsDrawable

    init {
        graphicsDrawable.setBackgroundMode(true)
        mGraphicsDrawable = graphicsDrawable
        mStartGraphicsDrawable = graphicsDrawable.copy()
        mFailedGraphicsDrawable = graphicsDrawable.copy()
    }

    override fun onLoadStarted(placeholder: Drawable?) {
        mStartGraphicsDrawable.setDrawable(placeholder)
        super.onLoadStarted(mStartGraphicsDrawable)
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        mFailedGraphicsDrawable.setDrawable(errorDrawable)
        super.onLoadFailed(mFailedGraphicsDrawable)
    }

    override fun setResource(resource: Drawable?) {
        mGraphicsDrawable.setDrawable(resource)
        view?.background = mGraphicsDrawable
    }
}