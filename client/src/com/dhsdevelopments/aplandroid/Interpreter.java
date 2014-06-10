package com.dhsdevelopments.aplandroid;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import org.gnu.apl.AplException;
import org.gnu.apl.Native;

import java.io.StringWriter;

public class Interpreter extends Activity
{
    private ListView resultList;
    private ResultListAdapter resultListAdapter;
    private EditText expressionEntry;
    private CustomKeyboard customKeyboard;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.interpreter );

        expressionEntry = (EditText)findViewById( R.id.expression_entry );
        Typeface typeface = Typeface.createFromAsset( getAssets(), "fonts/FreeMono.ttf" );
        expressionEntry.setTypeface( typeface );

        resultList = (ListView)findViewById( R.id.result_list_view );
        resultListAdapter = new ResultListAdapter( getLayoutInflater(), typeface );
        resultList.setAdapter( resultListAdapter );

        customKeyboard = new CustomKeyboard( this, R.id.keyboard_view, R.xml.layboard_layout );
        customKeyboard.registerEditText( R.id.expression_entry );
    }

    @Override
    public void onBackPressed() {
        if( customKeyboard != null && customKeyboard.isCustomKeyboardVisible() ) {
            customKeyboard.hideCustomKeyboard();
        }
        else {
            super.onBackPressed();
        }
    }

    public void sendClicked( View view ) {
        String expr = expressionEntry.getText().toString();
        expressionEntry.setText( "" );

        StringWriter writer = new StringWriter();
        try {
            Native.evalWithIo( expr, writer, writer, writer, writer );
            Log.i( "Result:" + writer.toString() );
            resultListAdapter.addEntry( expr, writer.toString() );
        } catch( AplException e ) {
            Log.i( "exception when evaluating expression", e );
        }
    }
}
