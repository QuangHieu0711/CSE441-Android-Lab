package com.example.webintentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        Uri uri = intent.getData();

        WebView webView = findViewById(R.id.webview1);
        if (uri != null) {
            webView.loadUrl(uri.toString());
        }
    }
}
