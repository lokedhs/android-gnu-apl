package com.dhsdevelopments.aplandroid;

import android.app.Application;

import java.io.File;
import java.io.IOException;

public class AplAndroidApp extends Application
{
    private AplNative aplNative;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            File destDir = Installer.install( this );
            aplNative = new AplNative( destDir );
        }
        catch( IOException e ) {
            throw new RuntimeException( "Exception when installing support files", e );
        }
    }

    public AplNative getAplNative() {
        return aplNative;
    }
}
