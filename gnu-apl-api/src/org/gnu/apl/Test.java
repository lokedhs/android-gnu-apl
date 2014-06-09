package org.gnu.apl;

import java.io.StringWriter;

public class Test
{
    public static void main( String[] args ) {
        try {
            Native.init();

            StringWriter cin = new StringWriter();
            StringWriter cout = new StringWriter();
            StringWriter cerr = new StringWriter();
            StringWriter uerr = new StringWriter();
            Native.evalWithIo( "1+2", cin, cout, cerr, uerr );
            System.out.printf( "cin='%s'%n", cin.toString() );
            System.out.printf( "cout='%s'%n", cout.toString() );
            System.out.printf( "cerr='%s'%n", cerr.toString() );
            System.out.printf( "uerr='%s'%n", uerr.toString() );
        } catch( AplException e ) {
            e.printStackTrace();
        }
    }
}
