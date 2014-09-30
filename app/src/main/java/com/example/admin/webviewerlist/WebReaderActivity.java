package com.example.admin.webviewerlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebReaderActivity extends Activity {

    protected String mUrl;
    protected String mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        mUrl = extras.getString("EXTRA_URL");
        mDescription = extras.getString("EXTRA_DESCRIPTION");

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(mUrl);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share) {
            sharePosts();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sharePosts() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mDescription+"\n"+mUrl);
        startActivity(Intent.createChooser(shareIntent, "How do you want to share?"));
    }
}