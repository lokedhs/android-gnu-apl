package com.dhsdevelopments.aplandroid;

import com.google.common.base.Preconditions;
import org.gnu.apl.AplException;

public class EvalRequest
{
    private String expr;
    private EvalRequestCallback callback;

    EvalRequest( String expr, EvalRequestCallback callback ) {
        Preconditions.checkNotNull( expr );
        this.expr = expr;
        this.callback = callback;
    }

    public String getExpr() {
        return expr;
    }

    public EvalRequestCallback getCallback() {
        return callback;
    }

    public interface EvalRequestCallback
    {
        void evalComplete( CharSequence result );

        void evalException( AplException exception );
    }
}
