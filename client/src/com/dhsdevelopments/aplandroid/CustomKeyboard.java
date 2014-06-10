package com.dhsdevelopments.aplandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * When an activity hosts a keyboardView, this class allows several EditText's to register for it.
 *
 * @author Maarten Pennings, extended by SimplicityApks
 * @date 2012 December 23
 */
class CustomKeyboard implements android.content.DialogInterface.OnClickListener
{

    /**
     * A link to the KeyboardView that is used to render this CustomKeyboard.
     */
    private KeyboardView mKeyboardView;
    /**
     * A link to the activity that hosts the {@link #mKeyboardView}.
     */
    private Activity mHostActivity;
    private boolean hapticFeedback;

    /**
     * The key (code) handler.
     */
    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener()
    {

        // add your own special keys here:
        public final static int CodeDelete = -5; // Keyboard.KEYCODE_DELETE
        public final static int CodeCancel = -3; // Keyboard.KEYCODE_CANCEL
        public final static int CodePrev = 55000;
        public final static int CodeAllLeft = 55001;
        public final static int CodeLeft = 55002;
        public final static int CodeRight = 55003;
        public final static int CodeAllRight = 55004;
        public final static int CodeNext = 55005;
        public final static int CodeClear = 55006;
        public final static int CodeConst = 55009;
        public final static int CodeLog = 55010;
        public final static int CodeConv = 55011; // Conversions like round or degrees
        public final static int CodeTrig = 55012;

        @Override
        public void onKey( int primaryCode, int[] keyCodes ) {
            // NOTE We can say '<Key android:codes="49,50" ... >' in the xml file; all codes come in keyCodes, the first in this list in primaryCode
            // Get the EditText and its Editable
            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
            if( focusCurrent == null || focusCurrent.getClass() != EditText.class ) {
                return;
            }
            EditText edittext = (EditText)focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            // delete the selection, if chars are selected:
            int end = edittext.getSelectionEnd();
            if( end > start ) {
                editable.delete( start, end );
            }
            // Apply the key to the edittext
            if( primaryCode == CodeCancel ) {
                hideCustomKeyboard();
            }
            else if( primaryCode == CodeDelete ) {
                if( editable != null && start > 0 ) {
                    editable.delete( start - 1, start );
                }
            }
            else if( primaryCode == CodeClear ) {
                if( editable != null ) {
                    editable.clear();
                }
            }
            else if( primaryCode == CodeLeft ) {
                if( start > 0 ) {
                    edittext.setSelection( start - 1 );
                }
            }
            else if( primaryCode == CodeRight ) {
                if( start < edittext.length() ) {
                    edittext.setSelection( start + 1 );
                }
            }
            else if( primaryCode == CodeAllLeft ) {
                edittext.setSelection( 0 );
            }
            else if( primaryCode == CodeAllRight ) {
                edittext.setSelection( edittext.length() );
            }
            else if( primaryCode == CodePrev ) {
                View focusNew = edittext.focusSearch( View.FOCUS_LEFT );
                if( focusNew != null ) {
                    focusNew.requestFocus();
                }
            }
            else if( primaryCode == CodeNext ) {
                View focusNew = edittext.focusSearch( View.FOCUS_RIGHT );
                if( focusNew != null ) {
                    focusNew.requestFocus();
                }
            }
/*
            else if( primaryCode == CodeConst ) {
                popupCode = CodeConst;
                showNewInsertDialog( R.array.insert_const );
            }
            else if( primaryCode == CodeLog ) {
                // idea for the log, use log2() for a log base 3
                popupCode = CodeLog;
                showNewInsertDialog( R.array.insert_log );
            }
            else if( primaryCode == CodeConv ) {
                popupCode = CodeConv;
                showNewInsertDialog( R.array.insert_conv );
            }
            else if( primaryCode == CodeTrig ) {
                popupCode = CodeTrig;
                showNewInsertDialog( R.array.insert_trig );
            }
*/
            else { // insert character
                editable.insert( start, Character.toString( (char)primaryCode ) );
            }
        }

        @Override
        public void onPress( int arg0 ) {
            // vibrate if haptic feedback is enabled:
            if( hapticFeedback && arg0 != 0 ) {
                mKeyboardView.performHapticFeedback( HapticFeedbackConstants.VIRTUAL_KEY );
            }
        }

        @Override
        public void onRelease( int primaryCode ) {
        }

        @Override
        public void onText( CharSequence text ) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    };

    /**
     * Create a custom keyboard, that uses the KeyboardView (with resource id <var>viewid</var>) of the <var>host</var> activity,
     * and load the keyboard layout from xml file <var>layoutid</var> (see {@link Keyboard} for description).
     * Note that the <var>host</var> activity must have a <var>KeyboardView</var> in its layout (typically aligned with the bottom of the activity).
     * Note that the keyboard layout xml file may include key codes for navigation; see the constants in this class for their values.
     * Note that to enable EditText's to use this custom keyboard, call the {@link #registerEditText(int)}.
     *
     * @param host     The hosting activity.
     * @param viewid   The id of the KeyboardView.
     * @param layoutid The id of the xml file containing the keyboard layout.
     */
    public CustomKeyboard( Activity host, int viewid, int layoutid ) {
        mHostActivity = host;
        mKeyboardView = (KeyboardView)mHostActivity.findViewById( viewid );
        mKeyboardView.setKeyboard( new Keyboard( mHostActivity, layoutid ) );
        mKeyboardView.setPreviewEnabled( false ); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener( mOnKeyboardActionListener );
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
    }

    /**
     * Returns whether the CustomKeyboard is visible.
     */
    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    /**
     * Make the CustomKeyboard visible, and hide the system keyboard for view v.
     */
    public void showCustomKeyboard( View v ) {
        mKeyboardView.setVisibility( View.VISIBLE );
        mKeyboardView.setEnabled( true );
        if( v != null ) {
            ((InputMethodManager)mHostActivity.getSystemService( Activity.INPUT_METHOD_SERVICE )).hideSoftInputFromWindow( v.getWindowToken(), 0 );
        }
    }

    /**
     * Make the CustomKeyboard invisible.
     */
    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility( View.GONE );
        mKeyboardView.setEnabled( false );
    }

    /**
     * Register <var>EditText<var> with resource id <var>resid</var> (on the hosting activity) for using this custom keyboard.
     *
     * @param resid The resource id of the EditText that registers to the custom keyboard.
     */
    public void registerEditText( int resid ) {
        // Find the EditText 'resid'
        final EditText edittext = (EditText)mHostActivity.findViewById( resid );
        // Make the custom keyboard appear
        edittext.setOnFocusChangeListener( new OnFocusChangeListener()
        {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                if( hasFocus ) {
                    showCustomKeyboard( v );
                }
                else {
                    hideCustomKeyboard();
                }
            }
        } );
        edittext.setOnClickListener( new OnClickListener()
        {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override
            public void onClick( View v ) {
                showCustomKeyboard( v );
            }
        } );
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener( new OnTouchListener()
        {
            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                EditText edittext = (EditText)v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType( InputType.TYPE_NULL ); // Disable standard keyboard
                edittext.onTouchEvent( event );               // Call native handler
                edittext.setInputType( inType );              // Restore input type
                edittext.setCursorVisible( true );
                return true; // Consume touch event
            }
        } );
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType( edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
        /**
         * Try to show cursor the complicated way:
         * @source http://androidpadanam.wordpress.com/2013/05/29/customkeyboard-example/
         * fixes the cursor not movable bug
         */
        OnTouchListener otl = new OnTouchListener()
        {

            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                if( !(isCustomKeyboardVisible()) ) {
                    showCustomKeyboard( v );
                }
                switch( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        Layout layout = ((EditText)v).getLayout();
                        float x = event.getX() + edittext.getScrollX();
                        int offset = layout.getOffsetForHorizontal( 0, x );
                        if( offset > 0 ) {
                            if( x > layout.getLineMax( 0 ) ) {
                                edittext.setSelection( offset );     // touch was at the end of the text
                            }
                            else {
                                edittext.setSelection( offset - 1 );
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        layout = ((EditText)v).getLayout();
                        x = event.getX() + edittext.getScrollX();
                        offset = layout.getOffsetForHorizontal( 0, x );
                        if( offset > 0 ) {
                            if( x > layout.getLineMax( 0 ) ) {
                                edittext.setSelection( offset );     // Touchpoint was at the end of the text
                            }
                            else {
                                edittext.setSelection( offset - 1 );
                            }
                        }
                        break;

                }
                return true;
            }
        };
        edittext.setOnTouchListener( otl );
    }

/*
 * Added methods: (may need additional resource files)
 */

    /**
     * Shows a simple dialog containing a list from the itemsId resource.
     * The onClickListener is set to this, see onClick(...) below
     */
    private void showNewInsertDialog( int itemsId ) {
        AlertDialog.Builder builder = new AlertDialog.Builder( mHostActivity );
        builder.setItems( itemsId, this );
        builder.create().show();
    }

    /**
     * @param popupCode Code indicating which popup is currently opened for the onClick getter
     */
    private int popupCode;

    @Override
    public void onClick( DialogInterface dialog, int which ) { // insert one of the additional popups to the current Focus field
        String insertion = "";
        switch( popupCode ) {
            /*
            case 55009:
                insertion = mHostActivity.getResources().getStringArray( R.array.insert_const_values )[which];
                break;
            case 55010:
                insertion = mHostActivity.getResources().getStringArray( R.array.insert_log_values )[which];
                break;
            case 55011:
                insertion = mHostActivity.getResources().getStringArray( R.array.insert_conv_values )[which];
                break;
            case 55012:
                insertion = mHostActivity.getResources().getStringArray( R.array.insert_trig_values )[which];
                break;
                */
        }
        View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
        if( focusCurrent == null || focusCurrent.getClass() != EditText.class ) {
            return;
        }
        EditText edittext = (EditText)focusCurrent;
        Editable editable = edittext.getText();
        int start = edittext.getSelectionStart();
        // delete the selection, if chars are selected:
        int end = edittext.getSelectionEnd();
        editable.replace( start, end, insertion );
    }

    /**
     * Enables or disables the Haptic feedback on keyboard touches
     *
     * @param goEnabled true if you want haptic feedback, falso otherwise
     */
    public void enableHapticFeedback( boolean goEnabled ) {
        mKeyboardView.setHapticFeedbackEnabled( goEnabled );
        hapticFeedback = goEnabled;
    }
}