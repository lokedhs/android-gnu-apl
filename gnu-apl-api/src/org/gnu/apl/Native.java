package org.gnu.apl;

import java.io.StringWriter;

public class Native
{
    static {
        System.loadLibrary( "apl" );
    }

    public static native int init();

    public static native AplValue evalExpression( String expr ) throws AplException;

    public static native void evalWithIo( String s, StringWriter cin, StringWriter cout, StringWriter cerr ) throws AplException;
}
