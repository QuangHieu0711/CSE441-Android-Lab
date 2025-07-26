package com.example.sum_calculator_b1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editTextA = findViewById(R.id.editTextText);
        EditText editTextB = findViewById(R.id.editTextText2);
        TextView resultTextView = findViewById(R.id.resultTextView);
        if (resultTextView == null) {
            throw new IllegalStateException("resultTextView is not found in the layout file.");
        }
        Button sumButton = findViewById(R.id.btn);

        sumButton.setOnClickListener(v -> {
            try {
                int numberA = Integer.parseInt(editTextA.getText().toString());
                int numberB = Integer.parseInt(editTextB.getText().toString());
                int sum = numberA + numberB;
                resultTextView.setText(getString(R.string.result_label, sum));
            } catch (NumberFormatException e) {
                resultTextView.setText(getString(R.string.error_invalid_input));
            }
        });
    }
}