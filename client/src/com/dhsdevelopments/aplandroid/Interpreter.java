package com.dhsdevelopments.aplandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import org.gnu.apl.AplException;
import org.gnu.apl.Native;

import java.io.StringWriter;

public class Interpreter extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.interpreter );
    }

    public void sendClicked( View view ) {
        EditText expressionEntry = (EditText)findViewById( R.id.expressionEntry );
        String expr = expressionEntry.getText().toString();

        StringWriter writer = new StringWriter();
        try {
            Native.evalWithIo( expr, writer, writer, writer, writer );
            Log.i( "Result:" + writer.toString() );
        } catch( AplException e ) {
            Log.i( "exception when evaluating expression", e );
        }
    }
}
