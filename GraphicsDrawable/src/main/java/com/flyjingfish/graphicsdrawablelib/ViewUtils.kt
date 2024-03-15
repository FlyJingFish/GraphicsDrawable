package com.flyjingfish.graphicsdrawablelib

import android.util.LayoutDirection
import android.view.View
import androidx.core.text.TextUtilsCompat
import java.util.Locale

internal object ViewUtils {
    fun getViewPaddingLeft(view: View): Int {
        var isRtl: Boolean = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL
        val paddingStart = view.paddingStart
        val paddingEnd = view.paddingEnd
        val paddingLeft = view.paddingLeft
        val paddingLeftMax: Int = if (isRtl) {
            if (paddingEnd != 0) {
                paddingEnd
            } else {
                paddingLeft
            }
        } else {
            if (paddingStart != 0) {
                paddingStart
            } else {
                paddingLeft
            }
        }
        return paddingLeftMax
    }

    fun getViewPaddingRight(view: View): Int {
        var isRtl: Boolean = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL
        val paddingStart = view.paddingStart
        val paddingEnd = view.paddingEnd
        val paddingRight = view.paddingRight
        val paddingRightMax: Int = if (isRtl) {
            if (paddingStart != 0) {
                paddingStart
            } else {
                paddingRight
            }
        } else {
            if (paddingEnd != 0) {
                paddingEnd
            } else {
                paddingRight
            }
        }
        return paddingRightMax
    }

    fun getRtlValue(startEndValue: Float, leftRightValue: Float): Float {
        val value: Float = if (startEndValue != 0f) {
            startEndValue
        } else {
            leftRightValue
        }
        return value
    }
}