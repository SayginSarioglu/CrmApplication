package com.example2.diablove.yakamozrehberi.ServerRequests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example2.diablove.yakamozrehberi.HelperClasses.Company;
import com.example2.diablove.yakamozrehberi.HelperClasses.GPSTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Diablove on 8/4/2016.
 */
public class CompanyBackgroundTask {
    public Context context;
    public ArrayList<Company> arrayList = new ArrayList<>();
    public double latitude= 39.209385,longitude =  28.933291;
    public GPSTracker gps;
    public String json_url="";

    public CompanyBackgroundTask(Context context) {
        this.context = context;
        gps = new GPSTracker(this.context);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            Log.d(json_url, json_url) ;
        }
        json_url = "http://crm.befasotomasyon.com/yakinlardakiler_json.php?enlem=" + latitude + "&boylam=" + longitude + "";
        Log.d(json_url,json_url) ;

    }
    public ArrayList<Company> getList(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count<response.length()){
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                Company company = new Company(jsonObject.getString("firma_kodu"),jsonObject.getString("kisa_ad"),jsonObject.getString("enlem"),jsonObject.getString("boylam"));

                                arrayList.add(company);
                                count++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error from server", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        CompanySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);

        return arrayList;
    }
}
