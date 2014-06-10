package com.dhsdevelopments.aplandroid.input2;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.dhsdevelopments.aplandroid.R;

/**
 * Displays the IME preferences inside the input method setting.
 */
public class ImePreferences extends PreferenceActivity
{
    @Override
    public Intent getIntent() {
        final Intent modIntent = new Intent( super.getIntent() );
        modIntent.putExtra( EXTRA_SHOW_FRAGMENT, Settings.class.getName() );
        modIntent.putExtra( EXTRA_NO_HEADERS, true );
        return modIntent;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        // We overwrite the title of the activity, as the default one is "Voice Search".
        setTitle( "Settings" );
    }

    public static class Settings extends InputMethodSettingsFragment
    {
        @Override
        public void onCreate( Bundle savedInstanceState ) {
            super.onCreate( savedInstanceState );
            setInputMethodSettingsCategoryTitle( R.string.language_selection_title );
            setSubtypeEnablerTitle( R.string.select_language );

            // Load the preferences from an XML resource
            addPreferencesFromResource( R.xml.ime_preferences );
        }
    }
}
