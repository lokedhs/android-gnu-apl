package com.dhsdevelopments.aplandroid.input2;

import android.graphics.Paint;
import android.graphics.Typeface;

public class PaintOverride extends Paint
{
    private Typeface overrideTypeface;

    public PaintOverride( Paint paint, Typeface overrideTypeface ) {
        super( paint );
        this.overrideTypeface = overrideTypeface;
    }

    @Override
    public Typeface setTypeface( Typeface typeface ) {
        return super.setTypeface( overrideTypeface == null ? typeface : overrideTypeface );
    }
}
