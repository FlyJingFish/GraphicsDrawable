package com.flyjingfish.graphicsdrawablecoillib

import android.graphics.drawable.Drawable
import android.view.View
import coil.target.GenericViewTarget

open class ViewBackgroundTarget(override val view: View) : GenericViewTarget<View>() {

    override var drawable: Drawable?
        get() = view.background
        set(value) {
            view.background = value
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is ViewBackgroundTarget && view == other.view
    }

    override fun hashCode() = view.hashCode()
}