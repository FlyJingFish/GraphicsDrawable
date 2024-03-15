package com.flyjingfish.graphicsdrawableglidelib

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable

open class GlideGraphicsImageViewTarget(private val mGraphicsDrawable: GraphicsDrawable) :
    ImageViewTarget<Drawable?>(mGraphicsDrawable.view as ImageView) {
    private val mStartGraphicsDrawable: GraphicsDrawable = mGraphicsDrawable.copy()
    private val mFailedGraphicsDrawable: GraphicsDrawable = mGraphicsDrawable.copy()

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
        view.setImageDrawable(mGraphicsDrawable)
    }
}