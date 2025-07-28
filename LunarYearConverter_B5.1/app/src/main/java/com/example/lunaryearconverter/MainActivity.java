package com.example.lunaryearconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editYear;
    TextView textLunarYear;
    Button btnConvert;

    String[] can = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
    String[] chi = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mẹo", "Thìn", "Tỵ", "Ngọ", "Mùi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editYear = findViewById(R.id.editYear);
        textLunarYear = findViewById(R.id.textLunarYear);
        btnConvert = findViewById(R.id.btnConvert);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Integer.parseInt(editYear.getText().toString());
                String canStr = can[year % 10];
                String chiStr = chi[year % 12];
                textLunarYear.setText(canStr + " " + chiStr);
            }
        });
    }
}
