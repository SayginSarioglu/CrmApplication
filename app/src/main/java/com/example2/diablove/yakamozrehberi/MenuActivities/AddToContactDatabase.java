package com.example2.diablove.yakamozrehberi.MenuActivities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example2.diablove.yakamozrehberi.HelperClasses.Contact;
import com.example2.diablove.yakamozrehberi.R;
import com.example2.diablove.yakamozrehberi.ServerRequests.ContactUploadRequest;

import org.json.JSONObject;

import java.util.Arrays;

public class AddToContactDatabase extends AppCompatActivity implements View.OnClickListener {

    Contact contact;
    Button rehberdenSec, bregister;
    EditText adSoyad,  mobile, work,fax,  emailText, title, company ;


    private static final String DEBUG_TAG = "ChooseActivity";
    private static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        adSoyad = (EditText)findViewById(R.id.ad_soyad);
        mobile = (EditText)findViewById(R.id.mobil);
        work = (EditText)findViewById(R.id.work);
        fax = (EditText) findViewById(R.id.fax);
        emailText = (EditText)findViewById(R.id.email);
        company = (EditText)findViewById(R.id.company);
        title = (EditText)findViewById(R.id.unvan);
        bregister = (Button) findViewById(R.id.upload);

        rehberdenSec = (Button) findViewById(R.id.do_email_picker);
        bregister.setOnClickListener(this);

        getPermissionToReadUserContacts();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload:
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("response: ", response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(AddToContactDatabase.this, "İşlem Başarılı", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AddToContactDatabase.this, "İşlem Başarısız", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddToContactDatabase.this);
                                builder.setMessage("İşlem Başarısız").setNegativeButton("Bağlantıları Kontrol Edin", null).create().show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                ContactUploadRequest contactUploadRequest = new ContactUploadRequest(contact, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddToContactDatabase.this);
                queue.add(contactUploadRequest);
        }
    }

    public void doLaunchContactPicker(View view) {

        adSoyad.setText("");
        mobile.setText("");
        work.setText("");
        fax.setText("");
        emailText.setText("");
        company.setText("");
        title.setText("");
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    String email = "";
                    try {



                        Uri contactData = data.getData();
                        Cursor c = getContentResolver().query(contactData, null, null, null, null);
                        if (c != null) {
                            if (c.moveToFirst())
                            {
                                /**********************************************************************/

                                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                                if (hasPhone.equalsIgnoreCase("1"))
                                {
                                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                    /*phones.moveToFirst();
                                    String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));*/
                                    if (phones != null) {
                                        while (phones.moveToNext())
                                        {
                                            int phone_type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                            switch (phone_type)
                                            {
                                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                                    String phoneHome =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                                    mobile.setText(phoneHome);
                                                    break;
                                                case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                                                    String phone_mob=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                                    fax.setText(phone_mob);
                                                    break;
                                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:

                                                    String phone_work=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                                    work.setText(phone_work);
                                                    break;
                                            }
                                        }
                                    }
                                    String nameContact = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                                    adSoyad.setText(nameContact);



                                }
                            }
                        }

                        /**********************************************************************/

                        String id = contactData.getLastPathSegment();
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] { id },
                                null);

                        int emailIdx = 0;
                        if (cursor != null) {
                            emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                        }

                        if (cursor != null) {
                            if (cursor.moveToFirst()) {

                                email = cursor.getString(emailIdx);

                                Log.v(DEBUG_TAG, "Got email: " + Arrays.toString(cursor.getColumnNames()));

                            } else {
                                Log.w(DEBUG_TAG, "No results");
                            }
                        }


                        /**********************************************************************/



                        Cursor cCur = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                                null,
                                ContactsContract.Data.CONTACT_ID
                                        + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?",
                                new String[]{id,
                                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE},
                                null);
                        if (cCur != null) {
                            while(cCur.moveToNext()) {
                                String orgName = cCur.getString(cCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                                Log.v("kalaba: ", orgName);
                                String stitle = cCur.getString(cCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                                title.setText(stitle);
                                company.setText(orgName);
                            }
                        }


                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, "Failed to get email data", e);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        emailText.setText(email);
                        if (email.length() == 0) {
                            Toast.makeText(this, "No email found for contact.",
                                    Toast.LENGTH_LONG).show();
                        }

                        contact = new Contact(adSoyad.getText().toString(), mobile.getText().toString(),work.getText().toString(),fax.getText().toString(),emailText.getText().toString(),company.getText().toString(),title.getText().toString());


                    }

                    break;
            }

        } else {
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    @TargetApi(Build.VERSION_CODES.M)
    public void getPermissionToReadUserContacts() {


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {


            if (shouldShowRequestPermissionRationale(
                    android.Manifest.permission.READ_CONTACTS)) {
                //İzinzde problem çıkarsa
            }

            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }
    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
