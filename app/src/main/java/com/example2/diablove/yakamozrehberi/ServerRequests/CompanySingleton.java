package com.example2.diablove.yakamozrehberi.ServerRequests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Diablove on 8/4/2016.
 */
public class CompanySingleton {

    private static CompanySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    public CompanySingleton(Context context) {
        this.mCtx = context;
        this.requestQueue = getRequestQueue();
    }


    public RequestQueue getRequestQueue(){

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized CompanySingleton getmInstance(Context context){
        if(mInstance == null){
            mInstance = new CompanySingleton(context);

        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

}
