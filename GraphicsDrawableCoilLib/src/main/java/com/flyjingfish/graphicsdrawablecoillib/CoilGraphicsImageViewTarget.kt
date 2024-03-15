package com.flyjingfish.graphicsdrawablecoillib

import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.target.ImageViewTarget
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable

class CoilGraphicsImageViewTarget(view: ImageView, private val mGraphicsDrawable: GraphicsDrawable) : ImageViewTarget(view) {
    private val mStartGraphicsDrawable: GraphicsDrawable = mGraphicsDrawable.copy()
    private val mFailedGraphicsDrawable: GraphicsDrawable = mGraphicsDrawable.copy()

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