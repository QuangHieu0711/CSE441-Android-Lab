package com.example.intentptb1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView txtKetQua;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtKetQua = findViewById(R.id.txtketqua);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        double a = intent.getDoubleExtra("hsa", 0);
        double b = intent.getDoubleExtra("hsb", 0);

        String result;
        if (a == 0) {
            result = (b == 0) ? "Vô số nghiệm" : "Vô nghiệm";
        } else {
            double x = -b / a;
            result = "x = " + x;
        }
        txtKetQua.setText(result);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
