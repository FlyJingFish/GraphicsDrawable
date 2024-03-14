package com.flyjingfish.shapedrawable;

import android.app.Application;


public class MyApplication extends Application {
    public static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
