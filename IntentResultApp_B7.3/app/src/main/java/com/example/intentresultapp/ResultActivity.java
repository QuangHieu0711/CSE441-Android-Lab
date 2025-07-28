package com.example.intentresultapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    EditText txtA, txtB;
    Button btnTong, btnHieu;
    double a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtA = findViewById(R.id.nhana);
        txtB = findViewById(R.id.nhanb);
        btnTong = findViewById(R.id.btntong);
        btnHieu = findViewById(R.id.btnhieu);

        Intent intent = getIntent();
        a = intent.getDoubleExtra("a", 0);
        b = intent.getDoubleExtra("b", 0);

        txtA.setText(String.valueOf(a));
        txtB.setText(String.valueOf(b));

        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double tong = a + b;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", tong);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double hieu = a - b;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", hieu);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}