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

    String userId,urlName,description;

    Button commit;

    String username,pwd;

    List<Site> siteList;

    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);


        user_id = (EditText) findViewById(R.id.user_id);
        url = (EditText) findViewById(R.id.url);
        desc = (EditText) findViewById(R.id.description);

        requestQueue = Volley.newRequestQueue(this);

    }



        public void onClick(View view) {

            userId = user_id.getText().toString();
            urlName = url.getText().toString();
            description = desc.getText().toString();

            try {
                if (isOnline()) {
                    postData("http://api.nilsp.in/api/v1/url/");
                    Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MyActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Network isn't available", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }



    private void postData(String uri) {

        StringRequest postRequest = new StringRequest(
                Request.Method.POST,
                uri,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP Response", response);
                        //siteList = SiteJSONParser.parseFeed(response);

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
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                return createBasicAuthHeader("demouser1", "demopass1");
            }


            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("id", "2");
                params.put("user_id", userId);
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
        Log.d("HTTP Response", base64EncodedCredentials);

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
