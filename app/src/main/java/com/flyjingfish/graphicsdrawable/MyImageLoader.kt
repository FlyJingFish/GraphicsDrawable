package com.flyjingfish.graphicsdrawable

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.flyjingfish.graphicsdrawablecoillib.setGraphicsImageViewDrawable
import com.flyjingfish.graphicsdrawablecoillib.setGraphicsViewBackground
import com.flyjingfish.graphicsdrawableglidelib.GlideGraphicsImageViewTarget
import com.flyjingfish.graphicsdrawableglidelib.GlideGraphicsViewBackgroundTarget
import com.flyjingfish.graphicsdrawablelib.GraphicsDrawable

object MyImageLoader {
    var loadType: LoaderType = LoaderType.GLIDE
    var useDrawable = true
    var backgroundMode = false

    enum class LoaderType {
        GLIDE, COIL
    }

    fun load(
        url: String,
        iv: ImageView,
        @DrawableRes p: Int,
        @DrawableRes err: Int,
        graphicsDrawable: GraphicsDrawable
    ) {
        if (loadType == LoaderType.GLIDE) {
            intoGlide(url, iv, p, err, graphicsDrawable)
        } else {
            intoCoil(url, iv, p, err, graphicsDrawable)
        }
    }

    private fun intoCoil(
        url: String,
        iv: ImageView,
        @DrawableRes p: Int,
        @DrawableRes err: Int,
        graphicsDrawable: GraphicsDrawable
    ) {
//        val imageLoader = Coil.imageLoader(iv.context)
//        val requestBuilder = ImageRequest.Builder(iv.context)
//            .data(url)
//
//        if (p != -1) requestBuilder.placeholder(p)
//        if (err != -1) requestBuilder.error(err)
//
//        if (useDrawable) {
//            if (backgroundMode){
//                requestBuilder.target(CoilGraphicsViewBackgroundTarget(graphicsDrawable))
//            }else{
//                requestBuilder.target(CoilGraphicsImageViewTarget(graphicsDrawable))
//            }
//        } else {
//            requestBuilder.target(iv)
//        }
//        val request = requestBuilder.build()
//        imageLoader.enqueue(request)

        iv.load(url){
            if (p != -1) placeholder(p)
            if (err != -1) error(err)
            if (useDrawable) {
                if (backgroundMode){
                    setGraphicsViewBackground(graphicsDrawable)
                }else{
                    setGraphicsImageViewDrawable(graphicsDrawable)
                }
            }
        }
    }

    private fun intoGlide(
        url: String,
        iv: ImageView,
        @DrawableRes p: Int,
        @DrawableRes err: Int,
        graphicsDrawable: GraphicsDrawable
    ) {
        val requestBuilder: RequestBuilder<Drawable> = Glide.with(iv).load(url)
        val mRequestOptions = RequestOptions()
        if (p != -1) mRequestOptions.placeholder(p)
        if (err != -1) mRequestOptions.error(err)
        requestBuilder.apply(mRequestOptions)
        if (useDrawable) {
            if (backgroundMode){
                requestBuilder.into(GlideGraphicsViewBackgroundTarget(graphicsDrawable))
            }else{
                requestBuilder.into(GlideGraphicsImageViewTarget(graphicsDrawable))
            }
        } else {
            requestBuilder.into(iv)
        }
    }

}