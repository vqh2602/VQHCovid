package com.example.vqhcovid;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {
    WebView webview_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        webview_map = (WebView)findViewById(R.id.webview_map);

        webview_map.loadUrl("https://bandodichte.bacgiang.gov.vn/bacgiang?locale=vn");
    }
}