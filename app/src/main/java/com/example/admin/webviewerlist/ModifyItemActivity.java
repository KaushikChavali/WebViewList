package com.example.admin.webviewerlist;

/**
 * Created by admin on 9/29/2014.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
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
import java.util.Map;

public class ModifyItemActivity extends Activity {

    protected String mUrl;
    protected String mDescription;
    protected Integer mUrlID;

    EditText desc,url;

    SessionManager session;

    String urlName,description;

    String username,pwd;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyitem);

        url = (EditText) findViewById(R.id.url);
        desc = (EditText) findViewById(R.id.description);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        username = user.get(SessionManager.KEY_EMAIL);

        // email
        pwd = user.get(SessionManager.KEY_PASS);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        mUrl = extras.getString("EXTRA_URL");
        mDescription = extras.getString("EXTRA_DESCRIPTION");
        mUrlID = extras.getInt("EXTRA_URLID");


        EditText editText1 = (EditText)findViewById(R.id.url);
        editText1.setText(mUrl, TextView.BufferType.EDITABLE);

        EditText editText2 = (EditText)findViewById(R.id.description);
        editText2.setText(mDescription, TextView.BufferType.EDITABLE);
    }

    public void onClick(View view) {

        try {
            if (isOnline()) {
                requestQueue = Volley.newRequestQueue(this);

                urlName = url.getText().toString();
                description = desc.getText().toString();

                requestData("http://api.nilsp.in/api/v1/url/"+mUrlID);
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

    private void requestData(String uri) {

        StringRequest putRequest = new StringRequest(
                Request.Method.PUT,
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
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return createBasicAuthHeader(username,pwd);

            }


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("url", urlName);
                params.put("description", description);

                return params;
            }
        };
        putRequest.setShouldCache(false);
        requestQueue.add(putRequest);
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
