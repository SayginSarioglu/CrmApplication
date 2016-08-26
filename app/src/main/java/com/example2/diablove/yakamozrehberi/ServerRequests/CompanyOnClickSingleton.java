package com.example2.diablove.yakamozrehberi.ServerRequests;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Diablove on 8/24/2016.
 */
public class CompanyOnClickSingleton {

    private static CompanyOnClickSingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private CompanyOnClickSingleton(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized CompanyOnClickSingleton getmInstance(Context context){
        if(mInstance == null){
            mInstance = new CompanyOnClickSingleton(context);

        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }




}
