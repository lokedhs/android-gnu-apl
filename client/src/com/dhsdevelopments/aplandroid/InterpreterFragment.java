package com.dhsdevelopments.aplandroid;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
        resultListAdapter = new ResultListAdapter( getActivity(), inflater, typeface );
        resultList.setAdapter( resultListAdapter );
        resultList.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                resultEntryClicked( position );
            }
        } );
        resultListAdapter.registerDataSetObserver( new ResultUpdatedObserver() );

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resultListAdapter.close();
    }

    private void resultEntryClicked( int position ) {
        ResultListEntry entry = resultListAdapter.getResultListEntry( position );
        expressionEntry.setText( entry.getExpr() );
    }

    public void sendClicked() {
        String expr = expressionEntry.getText().toString().trim();
        expressionEntry.setText( "" );

        if( expr.length() > 0 ) {
            AplNative aplNative = ((AplAndroidApp)getActivity().getApplicationContext()).getAplNative();
            aplNative.evalAsAsync( expr, null );
        }
    }

    private class ResultUpdatedObserver extends DataSetObserver
    {
        @Override
        public void onChanged() {
            resultList.setSelection( resultListAdapter.getCount() - 1 );
        }
    }
}
