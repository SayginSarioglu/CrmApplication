package com.example2.diablove.yakamozrehberi.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example2.diablove.yakamozrehberi.Adapters.RecyclerAdapter;
import com.example2.diablove.yakamozrehberi.HelperClasses.Company;
import com.example2.diablove.yakamozrehberi.HelperClasses.GPSTracker;
import com.example2.diablove.yakamozrehberi.HelperClasses.GetUserCallBack;
import com.example2.diablove.yakamozrehberi.HelperClasses.MarkerInfo;
import com.example2.diablove.yakamozrehberi.R;
import com.example2.diablove.yakamozrehberi.ServerRequests.CompanyBackgroundTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CompaniesHaritaFragment extends Fragment implements OnMapReadyCallback {

    public double latitude, longitude;
    public SupportMapFragment supportMapFragment;
    public ArrayList<MarkerInfo> arrayList = new ArrayList<>();
    public ProgressDialog progress;
    public String mJson_url="";
    public GoogleMap mMap;
    public CompaniesHaritaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        progress = ProgressDialog.show(getActivity(), "Fabrikalar Alınıyor",
                "", true);
        GPSTracker gps;
        gps = new GPSTracker(getActivity());

        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d(latitude+"","+"+longitude);
        }else{
            gps.showSettingsAlert();
        }
        mJson_url = "http://crm.befasotomasyon.com/yakinlardakiler_json.php?enlem=" + latitude + "&boylam=" + longitude + "";


        supportMapFragment = SupportMapFragment.newInstance();

        android.support.v4.app.FragmentManager sfm = getChildFragmentManager();

        supportMapFragment.getMapAsync(CompaniesHaritaFragment.this);
        if (supportMapFragment.isAdded()) {
            sfm.beginTransaction().hide(supportMapFragment).commit();
        }
        sfm.beginTransaction().add(R.id.map, supportMapFragment).commit();
        return rootView;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        try {
            fetchAllCompanyData(new GetUserCallBack() {
                @Override
                public void json(JSONArray jsonArray) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jSon = jsonArray.getJSONObject(i);
                            String sirket_adi = jSon.getString("kisa_ad");
                            String enlem = jSon.getString("enlem");
                            String boylam = jSon.getString("boylam");
                            MarkerInfo markerInfo = new MarkerInfo(sirket_adi, enlem, boylam);
                            Log.d("Konum: ", sirket_adi);
                            arrayList.add(markerInfo);
                            mMap.addMarker(new MarkerOptions().title(arrayList.get(i).title).position(new LatLng(Double.parseDouble(arrayList.get(i).latitude), Double.parseDouble(arrayList.get(i).longitude))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        CompanyBackgroundTask companyBackgroundTask = new CompanyBackgroundTask(CompaniesHaritaFragment.this.getActivity());
        ArrayList<Company> arrayList1= companyBackgroundTask.getList();
        Log.d("HARİTASİZE:" , arrayList1.size()+"");
/*
        for(int i = 0; i < arrayList.size() ; i++){
            mMap.addMarker(new MarkerOptions().title(arrayList.get(i).title).position(new LatLng(Double.parseDouble(arrayList.get(i).latitude), Double.parseDouble(arrayList.get(i).longitude))));
        }
*/
        LatLng current = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().title("Konumum").position(current).icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 10));

    }




    public void fetchAllCompanyData( GetUserCallBack callback) throws ExecutionException, InterruptedException {

        new fetchUserDataAsyncClass(callback).execute();
    }

    public class fetchUserDataAsyncClass extends AsyncTask<Void, Void, JSONArray > {


        GetUserCallBack userCallback;
        JSONArray jsonArrayTemp = null;


        public fetchUserDataAsyncClass( GetUserCallBack userCallback) {

            this.userCallback = userCallback;
        }
        public static final int CONNECTION_TIMEOUT = 1000 * 15;
        @SuppressWarnings("deprecation")
        @Override
        protected JSONArray doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT * 10);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT * 10);
            HttpClient client = new DefaultHttpClient(httpRequestParams);

            HttpPost post = new HttpPost(mJson_url);

            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                jsonArrayTemp = new JSONArray(result);
                return jsonArrayTemp;

            }catch (Exception e) {
                Log.e("itemNum", "catch");
                e.printStackTrace();
            }
            return jsonArrayTemp;
        }


        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            progress.dismiss();

            super.onPostExecute(jsonArray);
            userCallback.json(jsonArray);
        }


    }



}