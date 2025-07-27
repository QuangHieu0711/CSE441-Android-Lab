package com.example.tempconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextCelsius, editTextFahrenheit;
    Button btnToCelsius, btnToFahrenheit, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // gắn layout từ res/layout/activity_main.xml

        // Gắn ID từ XML vào biến Java
        editTextCelsius = findViewById(R.id.editTextCelsius);
        editTextFahrenheit = findViewById(R.id.editTextFahrenheit);
        btnToCelsius = findViewById(R.id.btnToCelsius);
        btnToFahrenheit = findViewById(R.id.btnToFahrenheit);
        btnClear = findViewById(R.id.btnClear);

        // Nút chuyển Fahrenheit -> Celsius
        btnToCelsius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fStr = editTextFahrenheit.getText().toString();
                if (!fStr.isEmpty()) {
                    try {
                        double f = Double.parseDouble(fStr);
                        double c = (f - 32) * 5 / 9;
                        editTextCelsius.setText(String.format("%.2f", c));
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid Fahrenheit input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter Fahrenheit value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Nút chuyển Celsius -> Fahrenheit
        btnToFahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cStr = editTextCelsius.getText().toString();
                if (!cStr.isEmpty()) {
                    try {
                        double c = Double.parseDouble(cStr);
                        double f = (c * 9 / 5) + 32;
                        editTextFahrenheit.setText(String.format("%.2f", f));
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid Celsius input", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter Celsius value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Nút xóa
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextCelsius.setText("");
                editTextFahrenheit.setText("");
            }
        });
    }
}
