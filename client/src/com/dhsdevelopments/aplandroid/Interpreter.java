package com.dhsdevelopments.aplandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.gnu.apl.AplException;
import org.gnu.apl.Native;

import java.io.StringWriter;

public class Interpreter extends Activity
{
    private ListView resultList;
    private ResultListAdapter resultListAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.interpreter );

        resultList = (ListView)findViewById( R.id.result_list_view );
        resultListAdapter = new ResultListAdapter( getLayoutInflater() );
        resultList.setAdapter( resultListAdapter );
    }

    public void sendClicked( View view ) {
        EditText expressionEntry = (EditText)findViewById( R.id.expressionEntry );
        String expr = expressionEntry.getText().toString();

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
