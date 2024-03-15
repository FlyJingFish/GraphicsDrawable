package com.flyjingfish.graphicsdrawable

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        var mInstance: MyApplication? = null
    }
}