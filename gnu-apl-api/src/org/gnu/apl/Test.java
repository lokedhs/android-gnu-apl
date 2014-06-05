package org.gnu.apl;

public class Test
{
    public static void main( String[] args ) {
        try {
            Native.init();
            Native.evalExpression( "1+2" );
        }
        catch( AplException e ) {
            e.printStackTrace();
        }
    }
}
