package com.dhsdevelopments.aplandroid.input2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * This is a helper class for an IME's settings preference fragment. It's recommended for every
 * IME to have its own settings preference fragment which inherits this class.
 */
public abstract class InputMethodSettingsFragment extends PreferenceFragment
        implements InputMethodSettingsInterface
{
    private final InputMethodSettingsImpl mSettings = new InputMethodSettingsImpl();

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        final Context context = getActivity();
        setPreferenceScreen( getPreferenceManager().createPreferenceScreen( context ) );
        mSettings.init( context, getPreferenceScreen() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputMethodSettingsCategoryTitle( int resId ) {
        mSettings.setInputMethodSettingsCategoryTitle( resId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputMethodSettingsCategoryTitle( CharSequence title ) {
        mSettings.setInputMethodSettingsCategoryTitle( title );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubtypeEnablerTitle( int resId ) {
        mSettings.setSubtypeEnablerTitle( resId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubtypeEnablerTitle( CharSequence title ) {
        mSettings.setSubtypeEnablerTitle( title );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubtypeEnablerIcon( int resId ) {
        mSettings.setSubtypeEnablerIcon( resId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubtypeEnablerIcon( Drawable drawable ) {
        mSettings.setSubtypeEnablerIcon( drawable );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        mSettings.updateSubtypeEnabler();
    }
}
