package com.example2.diablove.yakamozrehberi.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example2.diablove.yakamozrehberi.ServerRequests.CompanyBackgroundTask;
import com.example2.diablove.yakamozrehberi.HelperClasses.Company;
import com.example2.diablove.yakamozrehberi.R;
import com.example2.diablove.yakamozrehberi.Adapters.RecyclerAdapter;
import com.example2.diablove.yakamozrehberi.ServerRequests.CompanyOnClickSingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CompaniesGalleryFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Company> arrayList = new ArrayList<>();
    String mJson_url = "http://crm.befasotomasyon.com/bilgi_sorgu_json.php?firma_kodu=";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(CompaniesGalleryFragment.this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) getActivity()
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        CompanyBackgroundTask companyBackgroundTask = new CompanyBackgroundTask(CompaniesGalleryFragment.this.getActivity());
        arrayList = companyBackgroundTask.getList();
        adapter = new RecyclerAdapter(arrayList, new RecyclerAdapter.OnItemClickListener() {
            @Override public void onItemClick(Company item) {

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, mJson_url + item.getcompCode(), null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                int count = 0;
                                while(count<response.length()){
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(count);
                                        final String name = jsonObject.getString("firma_adi");
                                        AlertDialog.Builder builder = new
                                                AlertDialog.Builder(getActivity());
                                        final TextView telnum = new TextView(getActivity().getApplicationContext());
                                        final String finalNumber = new String(jsonObject.getString("ulke_kodu"))+new String(jsonObject.getString("alan_kodu"))+new String(jsonObject.getString("numara"));
                                        telnum.setText(finalNumber);
                                        telnum.setTextColor(Color.BLACK);
                                        builder.setTitle("Ara ?");
                                        builder.setView(telnum);
                                        builder.setCancelable(true);
                                        builder.setNegativeButton("Cancel", null);
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:" + finalNumber));
                                                startActivity(callIntent);

                                            }
                                        });
                                        AlertDialog dialog = builder.create();

                                        dialog.show();

                                    } catch (JSONException e) {
                                    }
                                    count++;
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"wrooong",Toast.LENGTH_SHORT).show();
                    }
                });
                CompanyOnClickSingleton.getmInstance(CompaniesGalleryFragment.this.getActivity()).addToRequestQueue(jsonArrayRequest);
            }

        });
        recyclerView.setAdapter(adapter);
        Log.d("arsize: ", arrayList.size()+"");
        return view;
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getActivity().getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getActivity().getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }



}