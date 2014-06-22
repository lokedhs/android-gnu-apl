package com.dhsdevelopments.aplandroid;

import org.gnu.apl.AplException;
import org.gnu.apl.Native;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class AplNative
{
    private List<ResultListEntry> results = new ArrayList<>();
    private List<ResultListListener> resultListListeners = new CopyOnWriteArrayList<>();
    private BlockingQueue<EvalRequest> evalQueue = new LinkedBlockingQueue<>();
    private AplEvalThread evalThread;

    public AplNative( File path ) {
        Native.init( path.getPath() );
        evalThread = new AplEvalThread();
        evalThread.start();
    }

    public String eval( String expr ) throws AplException {
        String trimmed = expr.trim();
        if( trimmed.length() == 0 ) {
            return "";
        }

        StringWriter writer = new StringWriter();
        Native.evalWithIo( expr, writer, writer, writer, writer );
        String res = writer.toString();
        ResultListEntry entry = new ResultListEntry( expr, res );
        synchronized( this ) {
            results.add( entry );
        }
        for( ResultListListener l : resultListListeners ) {
            l.resultUpdated( entry );
        }
        return res;
    }

    public void evalAsAsync( String expr, EvalRequest.EvalRequestCallback callback ) {
        evalQueue.add( new EvalRequest( expr, callback ) );
    }

    public void addResultListListener( ResultListListener listener ) {
        resultListListeners.add( listener );
    }

    public void removeResultListListener( ResultListListener listener ) {
        if( !resultListListeners.remove( listener ) ) {
            throw new IllegalStateException( "Attempting to remove non existent listener" );
        }
    }

    /**
     * The return value of this list has to be processed inside a
     * synchronised block protecting this instance.
     *
     * @return the underlying result list
     */
    public List<ResultListEntry> getResultEntries() {
        return results;
    }

    public interface ResultListListener
    {
        void resultUpdated( ResultListEntry entry );
    }

    private class AplEvalThread extends Thread
    {
        @Override
        public void run() {
            try {
                while( !Thread.interrupted() ) {
                    EvalRequest req = evalQueue.take();
                    String result = null;
                    AplException ex = null;
                    try {
                        result = eval( req.getExpr() );
                    }
                    catch( AplException e ) {
                        ex = e;
                    }
                    if( req.getCallback() != null ) {
                        callCallback( result, ex, req.getCallback() );
                    }
                }
            }
            catch( InterruptedException e ) {
                Log.e( "Eval thread stopped", e );
            }
        }

        private void callCallback( String result, AplException ex, EvalRequest.EvalRequestCallback callback ) {
            if( ex == null ) {
                callback.evalComplete( result );
            }
            else {
                callback.evalException( ex );
            }
        }
    }
}
