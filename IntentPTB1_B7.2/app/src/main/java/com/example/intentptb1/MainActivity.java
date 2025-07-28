package com.example.intentptb1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText txtA, txtB;
    Button btnKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtA = findViewById(R.id.txta);
        txtB = findViewById(R.id.txtb);
        btnKetQua = findViewById(R.id.btnketqua);

        btnKetQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double a = Double.parseDouble(txtA.getText().toString());
                double b = Double.parseDouble(txtB.getText().toString());

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("hsa", a);
                intent.putExtra("hsb", b);
                startActivity(intent);
            }
        });
    }
}