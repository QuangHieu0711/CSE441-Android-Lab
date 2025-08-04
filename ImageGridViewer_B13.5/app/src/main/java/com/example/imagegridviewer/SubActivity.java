package com.example.imagegridviewer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SubActivity extends Activity {
    TextView txtName;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childlayout);

        txtName = findViewById(R.id.textView2);
        imgView = findViewById(R.id.imageView2);

        int pos = getIntent().getExtras().getInt("TITLE");
        txtName.setText(MainActivity.arrayName[pos]);
        imgView.setImageResource(MainActivity.imageName[pos]);
    }
}
