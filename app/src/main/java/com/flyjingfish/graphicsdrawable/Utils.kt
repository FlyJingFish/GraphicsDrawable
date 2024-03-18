package com.flyjingfish.graphicsdrawable

import android.util.TypedValue

val Int.dp
    get() =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            MyApplication.mInstance.resources.displayMetrics
        )