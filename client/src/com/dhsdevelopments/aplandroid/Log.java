package com.dhsdevelopments.aplandroid;

public class Log
{
    public static final String LOG_TAG = "aplandroid";

    private Log() {
        // Prevent instantiation
    }

    public static void e( String message ) {
        android.util.Log.e( LOG_TAG, message );
    }

    public static void e( String message, Throwable throwable ) {
        android.util.Log.e( LOG_TAG, message, throwable );
    }

    public static void i( String message ) {
        android.util.Log.i( LOG_TAG, message );
    }

    public static void i( String message, Throwable throwable ) {
        android.util.Log.i( LOG_TAG, message, throwable );
    }

}
