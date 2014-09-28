package com.example.admin.webviewerlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 9/27/2014.
 */
public class AddItemActivity extends Activity{


    EditText user_id,desc,url;

    //String userId,urlName,description;

    String urlName,description;

    String username,pwd;

    List<Site> siteList;

    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);


        //user_id = (EditText) findViewById(R.id.user_id);
        url = (EditText) findViewById(R.id.url);
        desc = (EditText) findViewById(R.id.description);

    }



    public void onClick(View view) {

        //userId = user_id.getText().toString();


        try {
            if (isOnline()) {
                requestQueue = Volley.newRequestQueue(this);

                urlName = url.getText().toString();
                description = desc.getText().toString();

                requestData("http://api.nilsp.in/api/v1/url/");
                Toast.makeText(getApplicationContext(), "Created Successfully!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MyActivity.class);

                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void requestData(String uri) {

        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                uri,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP Response", response);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Toast.makeText(AddItemActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return createBasicAuthHeader("demouser2","demopass2");

            }


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("id", "2");
                //params.put("user_id", "1");
                params.put("url", urlName);
                params.put("description", description);

                return params;
            }
        };

        requestQueue.add(postRequest);
    }

    private Map<String, String> createBasicAuthHeader(String username, String password) {
        Map<String, String> headerMap = new HashMap<String, String>();

        String credentials = username + ":" + password;
        String base64EncodedCredentials =
                Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + base64EncodedCredentials);
        Log.d("HTTP Response",base64EncodedCredentials);

        return headerMap;
    }




    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}