package com.dhsdevelopments.aplandroid;

public class ResultListEntry
{
    private String expr;
    private String result;

    public ResultListEntry( String expr, String result ) {
        this.expr = expr;
        this.result = result;
    }

    public String getExpr() {
        return expr;
    }

    public String getResult() {
        return result;
    }
}
