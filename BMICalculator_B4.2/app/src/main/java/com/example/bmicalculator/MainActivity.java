package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText editName, editHeight, editWeight, editBmi, editResult;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view theo ID mới trong XML
        editName = findViewById(R.id.editName);
        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);
        editBmi = findViewById(R.id.editBmi);
        editResult = findViewById(R.id.editResult);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double height = Double.parseDouble(editHeight.getText().toString());
                double weight = Double.parseDouble(editWeight.getText().toString());
                double bmi = weight / Math.pow(height, 2);

                String result;
                if (bmi < 18) {
                    result = "Bạn gầy";
                } else if (bmi < 24.9) {
                    result = "Bạn bình thường";
                } else if (bmi < 29.9) {
                    result = "Bạn béo phì độ 1";
                } else if (bmi < 34.9) {
                    result = "Bạn béo phì độ 2";
                } else {
                    result = "Bạn béo phì độ 3";
                }

                DecimalFormat df = new DecimalFormat("#.0");
                editBmi.setText(df.format(bmi));
                editResult.setText(result);
            }
        });
    }
}
