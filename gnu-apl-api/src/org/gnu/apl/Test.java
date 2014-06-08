package org.gnu.apl;

import java.io.StringWriter;

public class Test
{
    public static void main( String[] args ) {
        try {
            Native.init();
            //Native.evalExpression( "1+2" );

            StringWriter cin = new StringWriter();
            StringWriter cout = new StringWriter();
            StringWriter cerr = new StringWriter();
            Native.evalWithIo( "1+2", cin, cout, cerr );
            System.out.println( "cin=" + cin.toString() );
            System.out.println( "cout=" + cout.toString() );
            System.out.println( "cerr=" + cerr.toString() );
        } catch( AplException e ) {
            e.printStackTrace();
        }
    }
}
