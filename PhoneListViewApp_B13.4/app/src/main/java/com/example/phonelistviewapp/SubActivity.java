package com.example.phonelistviewapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    TextView txt_subphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        txt_subphone = findViewById(R.id.txt_subphone);

        // Lấy dữ liệu từ Intent
        String namephone = getIntent().getStringExtra("name");

        // Gán lên TextView
        txt_subphone.setText("Bạn đã chọn\n" + namephone);
    }
}
