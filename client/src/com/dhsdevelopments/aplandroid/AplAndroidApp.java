package com.dhsdevelopments.aplandroid;

import android.app.Application;
import org.gnu.apl.Native;

public class AplAndroidApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Native.init();
    }
}
