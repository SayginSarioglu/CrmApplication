package com.example2.diablove.yakamozrehberi.ServerRequests;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http....";
    private Map<String, String> parameters;


    public LoginRequest(String username, String password , Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null );
        parameters = new HashMap<>();
        parameters.put("username", username);
        parameters.put("password", password);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
