package com.example2.diablove.yakamozrehberi.ServerRequests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example2.diablove.yakamozrehberi.HelperClasses.Contact;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diablove on 8/1/2016.
 */
public class FirmalarRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://crm.befasotomasyon.com/kart_ekle.php";
    private Map<String, String> parameters;


    public FirmalarRequest(String latitude, String longitude ,  Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("latitude", latitude);
        parameters.put("longitude", longitude);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}




