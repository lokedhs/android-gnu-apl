package org.gnu.apl;

@SuppressWarnings("UnusedDeclaration")
public class AplException extends Exception
{
    public AplException() {
        super();
    }

    public AplException( String detailMessage ) {
        super( detailMessage );
    }

    public AplException( String detailMessage, Throwable throwable ) {
        super( detailMessage, throwable );
    }

    public AplException( Throwable throwable ) {
        super( throwable );
    }
}
