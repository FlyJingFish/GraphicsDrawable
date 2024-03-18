@file:JvmName("-SingletonGraphicsExtensions")
@file:Suppress("NOTHING_TO_INLINE")
package com.flyjingfish.graphicsdrawablecoillib


import coil.request.ImageRequest
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable

inline fun ImageRequest.Builder.setGraphicsImageViewDrawable(
    graphicsDrawable: GraphicsDrawable
): ImageRequest.Builder {
    return this.target(CoilGraphicsImageViewTarget(graphicsDrawable))
}

inline fun ImageRequest.Builder.setGraphicsViewBackground(
    graphicsDrawable: GraphicsDrawable
): ImageRequest.Builder {
    return this.target(CoilGraphicsViewBackgroundTarget(graphicsDrawable))
}
