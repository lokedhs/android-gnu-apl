package com.dhsdevelopments.aplandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class Interpreter extends Activity
{
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.interpreter );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.main_activity_actions, menu );
        return super.onCreateOptionsMenu( menu );
    }
}
