package com.dhsdevelopments.aplandroid;

import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultListAdapter implements ListAdapter
{
    private List<DataSetObserver> observers = new ArrayList<>();
    private List<ResultListEntry> entries = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Typeface typeface;

    public ResultListAdapter( LayoutInflater layoutInflater, Typeface typeface ) {
        this.layoutInflater = layoutInflater;
        this.typeface = typeface;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled( int position ) {
        return false;
    }

    @Override
    public void registerDataSetObserver( DataSetObserver observer ) {
        observers.add( observer );
    }

    @Override
    public void unregisterDataSetObserver( DataSetObserver observer ) {
        observers.remove( observer );
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem( int position ) {
        return entries.get( position );
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        View resultText;
        if( convertView != null && convertView.getId() == R.id.result_text_view ) {
            resultText = convertView;
        }
        else {
            resultText = layoutInflater.inflate( R.layout.result_text, parent, false );
        }

        ResultListEntry entry = entries.get( position );

        TextView expr = (TextView)resultText.findViewById( R.id.expr );
        expr.setTypeface( typeface );
        expr.setText( entry.getExpr() );

        TextView content = (TextView)resultText.findViewById( R.id.content );
        content.setTypeface( typeface );
        content.setText( entry.getResult() );

        return resultText;
    }

    @Override
    public int getItemViewType( int position ) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public void addEntry( String expr, String result ) {
        entries.add( new ResultListEntry( expr, result ) );
        for( DataSetObserver observer : observers ) {
            observer.onChanged();
        }
    }
}
