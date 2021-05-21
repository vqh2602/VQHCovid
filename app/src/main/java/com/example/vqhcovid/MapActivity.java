package com.example.vqhcovid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MapActivity extends AppCompatActivity {
        WebView webview_map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        webview_map = (WebView)findViewById(R.id.webview_map);
        WebSettings webSettings = webview_map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_map.loadUrl("https://maps.vnpost.vn/corona/#/app");
    }
}