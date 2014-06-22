package org.gnu.apl;

import java.io.Writer;

public class Native
{
    static {
        System.loadLibrary( "apl" );
    }

    public static native int init( String files );

    public static native AplValue evalExpression( String expr ) throws AplException;

    public static native void evalWithIo( String s, Writer cin, Writer cout, Writer cerr, Writer uerr ) throws AplException;
}
