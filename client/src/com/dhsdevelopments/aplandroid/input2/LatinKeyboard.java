package com.dhsdevelopments.aplandroid.input2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.view.inputmethod.EditorInfo;
import com.dhsdevelopments.aplandroid.R;

public class LatinKeyboard extends Keyboard
{
    private Key enterKey;
    private Key spaceKey;
    private Typeface typeface;

    public LatinKeyboard( Context context, int xmlLayoutResId ) {
        super( context, xmlLayoutResId );
        init( context );
    }

    public LatinKeyboard( Context context, int layoutTemplateResId,
                          CharSequence characters, int columns, int horizontalPadding ) {
        super( context, layoutTemplateResId, characters, columns, horizontalPadding );
        init( context );
    }

    private void init( Context context ) {
        typeface = Typeface.createFromAsset( context.getAssets(), "fonts/FreeMono.ttf" );
    }

    @Override
    protected Key createKeyFromXml( Resources res, Row parent, int x, int y,
                                    XmlResourceParser parser ) {
        Key key = new LatinKey( res, parent, x, y, parser, typeface );

        if( key.codes[0] == 10 ) {
            enterKey = key;
        }
        else if( key.codes[0] == ' ' ) {
            spaceKey = key;
        }
        return key;
    }

    /**
     * This looks at the ime options given by the current editor, to set the
     * appropriate label on the keyboard's enter key (if it has one).
     */
    void setImeOptions( Resources res, int options ) {
        if( enterKey == null ) {
            return;
        }

        switch( options & (EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION) ) {
            case EditorInfo.IME_ACTION_GO:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = res.getText( R.string.label_go_key );
                break;
            case EditorInfo.IME_ACTION_NEXT:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = res.getText( R.string.label_next_key );
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                enterKey.icon = res.getDrawable( R.drawable.sym_keyboard_search );
                enterKey.label = null;
                break;
            case EditorInfo.IME_ACTION_SEND:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = res.getText( R.string.label_send_key );
                break;
            default:
                enterKey.icon = res.getDrawable( R.drawable.sym_keyboard_return );
                enterKey.label = null;
                break;
        }
    }

    void setSpaceIcon( final Drawable icon ) {
        if( spaceKey != null ) {
            spaceKey.icon = icon;
        }
    }

    static class LatinKey extends Keyboard.Key
    {
        public LatinKey( Resources res, Row parent, int x, int y, XmlResourceParser parser, Typeface typeface ) {
            super( res, parent, x, y, parser );
//            if( label != null ) {
//                SpannableString s = new SpannableString( label );
//                s.setSpan( new CustomTypefaceSpan( "monospace", typeface ), 0, s.length(), 0 );
//                label = s;
//            }
        }

        /**
         * Overriding this method so that we can reduce the target area for the key that
         * closes the keyboard.
         */
        @Override
        public boolean isInside( int x, int y ) {
            return super.isInside( x, codes[0] == KEYCODE_CANCEL ? y - 10 : y );
        }
    }
}
