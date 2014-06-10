package com.dhsdevelopments.aplandroid.input1;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import com.dhsdevelopments.aplandroid.Log;
import com.dhsdevelopments.aplandroid.R;

import java.util.Arrays;

public class AplInput extends InputMethodService
{
    @Override
    public View onCreateInputView() {
        AplKeyboardView v = (AplKeyboardView)getLayoutInflater().inflate( R.layout.apl_input, null );
        v.setOnKeyboardActionListener( new KeyboardAction() );
        Keyboard keyboard = new Keyboard( this, R.xml.keyboard_layout );
        v.setKeyboard( keyboard );
        Log.i( "modifiers:" + keyboard.getModifierKeys() );
        Log.i( "keys:" + keyboard.getKeys() );
        return v;
    }

    private class KeyboardAction implements KeyboardView.OnKeyboardActionListener
    {
        @Override
        public void onPress( int primaryCode ) {
            Log.i( "onPress:" + primaryCode );
        }

        @Override
        public void onRelease( int primaryCode ) {
            Log.i( "onRelease:" + primaryCode );
        }

        @Override
        public void onKey( int primaryCode, int[] keyCodes ) {
            Log.i( "onKey:" + primaryCode + "," + Arrays.toString( keyCodes ) );
        }

        @Override
        public void onText( CharSequence text ) {
            Log.i( "onText:'" + text + "'" );
        }

        @Override
        public void swipeLeft() {
            Log.i( "swipeLeft" );
        }

        @Override
        public void swipeRight() {
            Log.i( "swipeRight" );
        }

        @Override
        public void swipeDown() {
            Log.i( "swipeDown" );
        }

        @Override
        public void swipeUp() {
            Log.i( "swipeUp" );
        }
    }
}
