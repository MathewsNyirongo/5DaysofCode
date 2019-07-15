package com.android.alc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ActivityB extends AppCompatActivity {

    private ProgressBar progressBar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        if(checkConnectivity()){
            webView = (WebView) findViewById(R.id.webview);
            progressBar = (ProgressBar)findViewById(R.id.progressBar1);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new myWebViewClient());
            webView.loadUrl("https://andela.com/alc/");
        }else {
            Toast.makeText(ActivityB.this, "No internet connection!", Toast.LENGTH_LONG).show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean checkConnectivity(){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    private class myWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon){
            webView.setVisibility(webView.INVISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url){
            progressBar.setVisibility(View.GONE);

            view.setVisibility(webView.VISIBLE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
            handler.proceed();
        }
    }
}
