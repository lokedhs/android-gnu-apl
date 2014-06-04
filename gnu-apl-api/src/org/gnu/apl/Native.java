package org.gnu.apl;

public class Native
{
    static {
        System.loadLibrary( "apl" );
    }

    public static native int init();
}
