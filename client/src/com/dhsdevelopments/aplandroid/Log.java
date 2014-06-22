package com.dhsdevelopments.aplandroid;

@SuppressWarnings("UnusedDeclaration")
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

    public static void w( String message ) {
        android.util.Log.w( LOG_TAG, message );
    }

    public static void w( String message, Throwable throwable ) {
        android.util.Log.w( LOG_TAG, message, throwable );
    }

    public static void i( String message ) {
        android.util.Log.i( LOG_TAG, message );
    }

    public static void i( String message, Throwable throwable ) {
        android.util.Log.i( LOG_TAG, message, throwable );
    }

    public static void d( String message ) {
        android.util.Log.d( LOG_TAG, message );
    }

    public static void d( String message, Throwable throwable ) {
        android.util.Log.d( LOG_TAG, message, throwable );
    }
}
