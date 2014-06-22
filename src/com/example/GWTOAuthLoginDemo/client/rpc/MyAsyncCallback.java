package com.example.GWTOAuthLoginDemo.client.rpc;


import com.allen_sauer.gwt.log.client.Log;
import com.example.GWTOAuthLoginDemo.client.ui.BusyPanel;
import com.example.GWTOAuthLoginDemo.client.util.ClientUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Shows a busy animaed gif image while doing rpc.
 * 
 * Adapted from: http://stackoverflow.com/questions/1309436/automatic-loading-indicator-when-calling-an-async-function
 * 
 * @author muquit@muquit.com Nov 27, 2012 8:18:49 PM
 */

public abstract class MyAsyncCallback<T> implements AsyncCallback<T>
{
    protected abstract void callService(AsyncCallback<T> cb);
    
    final BusyPanel busyPanel = new BusyPanel();
    
    public void go(String message)
    {
        showLoadingMessage(message);
        execute();
    }
    
    private void showLoadingMessage(String message)
    {
        busyPanel.startProcessing(message);
    }
    
    private void hideLoadingMessage()
    {
        busyPanel.stopProcessing();
    }
    
    private void execute()
    {
        callService(new AsyncCallback<T>()
        {

            @Override
            public void onFailure(Throwable caught)
            {
                ClientUtils.handleException(caught);
                hideLoadingMessage();
                MyAsyncCallback.this.onFailure(caught);
                /*
                if (retriesLeft <= 0)
                {
                    hideLoadingMessage();
                    MyAsyncCallback.this.onFailure(caught);
                }
                else
                {
                    execute(retriesLeft - 1);
                }
                */
            }

            @Override
            public void onSuccess(T result)
            {
                hideLoadingMessage();
                MyAsyncCallback.this.onSuccess(result);
            }
        });
    }
    
    public MyAsyncCallback()
    {
        // TODO Auto-generated constructor stub
    }
}
