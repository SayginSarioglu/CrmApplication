package com.example2.diablove.yakamozrehberi.ServerRequests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example2.diablove.yakamozrehberi.HelperClasses.Contact;

import java.util.HashMap;
import java.util.Map;

public class ContactUploadRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://crm.befasotomasyon.com/kart_ekle.php";
    private Map<String, String> parameters;


    public ContactUploadRequest(Contact contact ,  Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null );
        parameters = new HashMap<>();
        parameters.put("adsoyad", contact.getAdSoyad());
        parameters.put("mobil", contact.getMobil());
        parameters.put("is", contact.getIs());
        parameters.put("faks", contact.getFaks());
        parameters.put("email", contact.getEmail());
        parameters.put("sirket", contact.getSirket());
        parameters.put("unvan", contact.getUnvan());

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
