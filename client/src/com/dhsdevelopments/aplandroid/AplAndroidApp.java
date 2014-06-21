package com.dhsdevelopments.aplandroid;

import android.app.Application;

public class AplAndroidApp extends Application
{
    private AplNative aplNative;

    @Override
    public void onCreate() {
        super.onCreate();
        aplNative = new AplNative();
    }

    public AplNative getAplNative() {
        return aplNative;
    }
}
