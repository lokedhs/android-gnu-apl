package com.dhsdevelopments.aplandroid;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.gnu.apl.AplException;
import org.gnu.apl.Native;

import java.io.StringWriter;

public class InterpreterFragment extends Fragment
{
    private EditText expressionEntry;
    private ListView resultList;
    private ResultListAdapter resultListAdapter;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View rootView = inflater.inflate( R.layout.interpreter_log, container, false );

        expressionEntry = (EditText)rootView.findViewById( R.id.expression_entry );
        Typeface typeface = Typeface.createFromAsset( getActivity().getAssets(), "fonts/FreeMono.ttf" );
        expressionEntry.setTypeface( typeface );

        resultList = (ListView)rootView.findViewById( R.id.result_list_view );
        resultListAdapter = new ResultListAdapter( inflater, typeface );
        resultList.setAdapter( resultListAdapter );

        Button sendButton = (Button)rootView.findViewById( R.id.send_expr_button );
        sendButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v ) {
                sendClicked();
            }
        } );

        return rootView;
    }

    public void sendClicked() {
        String expr = expressionEntry.getText().toString().trim();
        expressionEntry.setText( "" );

        if( expr.length() > 0 ) {
            StringWriter writer = new StringWriter();
            try {
                Native.evalWithIo( expr, writer, writer, writer, writer );
                Log.i( "Result:" + writer.toString() );
                resultListAdapter.addEntry( expr, writer.toString() );
                resultList.setSelection( resultListAdapter.getCount() - 1 );
            }
            catch( AplException e ) {
                Log.i( "exception when evaluating expression", e );
            }
        }
    }
}
