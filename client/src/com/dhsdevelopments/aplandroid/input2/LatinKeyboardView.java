package com.dhsdevelopments.aplandroid.input2;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;
import com.dhsdevelopments.aplandroid.Log;

import java.lang.reflect.Field;

public class LatinKeyboardView extends KeyboardView
{
    static final int KEYCODE_OPTIONS = -100;

    public LatinKeyboardView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init();
    }

    public LatinKeyboardView( Context context, AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
        init();
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset( getContext().getAssets(), "fonts/FreeMono.ttf" );
        try {
            Field paintField = KeyboardView.class.getDeclaredField( "mPaint" );
            Log.i( "paintField = " + paintField );
            paintField.setAccessible( true );
            Log.i( "is accessible:" + paintField.isAccessible() );
            Paint origPaint = (Paint)paintField.get( this );
            PaintOverride newPaint = new PaintOverride( origPaint, typeface );
            paintField.set( this, newPaint );
        } catch( NoSuchFieldException e ) {
            Log.e( "can't find field", e );
        } catch( IllegalAccessException e ) {
            Log.e( "Error accessing paint member", e );
        }
    }

    @Override
    protected boolean onLongPress( Key key ) {
        if( key.codes[0] == Keyboard.KEYCODE_CANCEL ) {
            getOnKeyboardActionListener().onKey( KEYCODE_OPTIONS, null );
            return true;
        }
        else {
            return super.onLongPress( key );
        }
    }

    void setSubtypeOnSpaceKey( final InputMethodSubtype subtype ) {
        final LatinKeyboard keyboard = (LatinKeyboard)getKeyboard();
        keyboard.setSpaceIcon( getResources().getDrawable( subtype.getIconResId() ) );
        invalidateAllKeys();
    }
}
