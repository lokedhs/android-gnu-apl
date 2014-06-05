package org.gnu.apl;

@SuppressWarnings("UnusedDeclaration")
public class AplExecException extends AplException
{
    private String line1;
    private String line2;
    private String line3;

    public AplExecException( String line1, String line2, String line3 ) {
        super( line1 + "\n" + line2 + "\n" + line3 );

        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
    }
}
